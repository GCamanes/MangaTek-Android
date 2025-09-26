package com.groupany.manga.presentation.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.groupany.base.CustomFailure
import com.groupany.base.CustomResult
import com.groupany.manga.domain.usecases.IsFavoriteUseCase
import com.groupany.manga.domain.usecases.ToggleAFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MangaDetailViewModel @Inject constructor(
    private val isFavoriteUseCase: IsFavoriteUseCase,
    private val toggleAFavoriteUseCase: ToggleAFavoriteUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _uiState = MutableStateFlow(MangaDetailUiState())
    val uiState: StateFlow<MangaDetailUiState> = _uiState
    private val mangaId: String = savedStateHandle["id"] ?: error("Missing mangaId")

    init {
        loadUiState()
    }

    fun loadUiState() {
        viewModelScope.launch {
            isFavoriteUseCase(mangaId)
                .collect { result ->
                    _uiState.update {
                        when (result) {
                            is CustomResult.Success -> it.copy(
                                isFavorite = result.value,
                                mangaId = mangaId,
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
    val isFavorite: Boolean = false,
    val failure: CustomFailure? = null,
    val isLoading: Boolean = false,
    val mangaId: String = "",
)