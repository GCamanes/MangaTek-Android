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
}

data class MangaDetailUiState(
    val manga: MangaEntity? = null,
    val coverUrl: String? = null,
    val isFavorite: Boolean = false,
    val failure: CustomFailure? = null,
    val isLoading: Boolean = false,
)