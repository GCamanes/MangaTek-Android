package com.groupany.manga.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.groupany.base.CustomFailure
import com.groupany.base.CustomResult
import com.groupany.manga.domain.usecases.GetAllFavoritesUseCase
import com.groupany.manga.domain.usecases.ToggleAFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MangaDetailViewModel @Inject constructor(
    private val getAllFavoritesUseCase: GetAllFavoritesUseCase,
    private val toggleAFavoriteUseCase: ToggleAFavoriteUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(MangaDetailUiState())
    val uiState: StateFlow<MangaDetailUiState> = _uiState

    init {
        loadUiState()
    }

    fun loadUiState() {
        viewModelScope.launch {
            getAllFavoritesUseCase()
                .collect { result ->
                    _uiState.update {
                        when (result) {
                            is CustomResult.Success -> it.copy(
                                favorites = result.value,
                            )

                            is CustomResult.Failure -> it.copy(
                                favorites = emptySet(),
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
    val favorites: Set<String> = emptySet<String>(),
    val failure: CustomFailure? = null,
    val isLoading: Boolean = false,
) {
    fun isFavorite(id: String): Boolean = favorites.contains(id)
}