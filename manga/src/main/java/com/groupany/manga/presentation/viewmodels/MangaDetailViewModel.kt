package com.groupany.manga.presentation.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.groupany.base.CustomFailure
import com.groupany.base.CustomResult
import com.groupany.manga.domain.entities.MangaEntity
import com.groupany.manga.domain.usecases.GetMangaCoverParams
import com.groupany.manga.domain.usecases.GetMangaCoverUseCase
import com.groupany.manga.domain.usecases.GetMangaUseCase
import com.groupany.manga.domain.usecases.IsFavoriteUseCase
import com.groupany.manga.domain.usecases.ToggleAFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MangaDetailViewModel @Inject constructor(
    private val getMangaUseCase: GetMangaUseCase,
    private val getMangaCoverUseCase: GetMangaCoverUseCase,
    private val isFavoriteUseCase: IsFavoriteUseCase,
    private val toggleAFavoriteUseCase: ToggleAFavoriteUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _uiState = MutableStateFlow(MangaDetailUiState())
    val uiState: StateFlow<MangaDetailUiState> = _uiState
    private val mangaId: String = savedStateHandle["id"] ?: error("Missing mangaId")
    private val mangaPath: String = savedStateHandle["path"] ?: error("Missing mangaId")

    init {
        viewModelScope.launch {
            getMangaUseCase(mangaId)
                .onStart {
                    _uiState.update { it.copy(isLoading = true, failure = null) }
                }
                .collect { result ->
                    _uiState.update {
                        when (result) {
                            is CustomResult.Success -> it.copy(
                                manga = result.value,
                                isLoading = false,
                                failure = null,
                            )

                            is CustomResult.Failure -> it.copy(
                                failure = result.failure,
                                isLoading = false
                            )
                        }
                    }
                }
        }
        viewModelScope.launch {
            val coverUrl = getMangaCoverUseCase(GetMangaCoverParams(mangaId, mangaPath))
            _uiState.update { it.copy(coverUrl = coverUrl.getOrNull()?.cachedUrl) }
        }
        viewModelScope.launch {
            isFavoriteUseCase(mangaId)
                .collect { result ->
                    _uiState.update {
                        when (result) {
                            is CustomResult.Success -> it.copy(
                                isFavorite = result.value,
                            )

                            is CustomResult.Failure -> it.copy(
                                isFavorite = false,
                            )
                        }
                    }
                }
        }
    }

    suspend fun toggleFavorite(id: String) {
        toggleAFavoriteUseCase.invoke(id)
    }

    fun saveStableTitleY(px: Float, animationFinished: Boolean) {
        if (animationFinished && _uiState.value.titleY == null) {
            _uiState.update { it.copy(titleY = px) }
            println("TitleY updated = ${_uiState.value.titleY}")
        }
    }

    fun updateUI(scrollOffset: Float, appBarPx: Float) {
        viewModelScope.launch {
            if (_uiState.value.titleY != null) {
                val titleY = _uiState.value.titleY!!
                // Global alpha based on scroll offset
                val alpha = (scrollOffset / (titleY - appBarPx)).coerceIn(0f, 1f)
                // Second alpha based on when title is near app bar
                val titleMaxPosition = titleY - appBarPx
                val titleMinPosition = titleY - appBarPx * 2
                val secondAlpha = when {
                    scrollOffset < titleMinPosition -> 0f
                    scrollOffset > titleMaxPosition -> appBarPx
                    else -> ((scrollOffset - titleMinPosition) / appBarPx).coerceIn(0f, 1f)
                }
                // Condition to animate title from header to app bar title
                val switchTitle = scrollOffset + appBarPx > titleY
                _uiState.update { current ->
                    current.takeIf {
                        it.alpha == alpha &&
                                it.secondAlpha == secondAlpha &&
                                it.switchTitle == switchTitle
                    } ?: current.copy(
                        alpha = alpha,
                        secondAlpha = secondAlpha,
                        switchTitle = switchTitle
                    )
                }
            }
        }
    }
}

data class MangaDetailUiState(
    val manga: MangaEntity? = null,
    val coverUrl: String? = null,
    val isFavorite: Boolean = false,
    val failure: CustomFailure? = null,
    val isLoading: Boolean = false,
    val titleY: Float? = null,
    val alpha: Float = 0f,
    val secondAlpha: Float = 0f,
    val switchTitle: Boolean = false,
)