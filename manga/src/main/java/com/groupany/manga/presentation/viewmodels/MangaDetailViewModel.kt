package com.groupany.manga.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.groupany.base.CustomFailure
import com.groupany.manga.domain.usecases.GetFavoritesUseCase
import com.groupany.manga.domain.usecases.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MangaDetailViewModel @Inject constructor(
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(MangaDetailUiState())
    val uiState: StateFlow<MangaDetailUiState> = _uiState

    init {
        loadUiState()
    }

    fun loadUiState() {
        viewModelScope.launch {
            _uiState.update { it.copy(favorites = getFavoritesUseCase()) }
        }
    }

    fun toggleFavorite(id: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(favorites = toggleFavoriteUseCase(id)) }
        }
    }
}

data class MangaDetailUiState(
    val favorites: Set<String> = emptySet<String>(),
    val failure: CustomFailure? = null,
    val isLoading: Boolean = false,
) {
    fun isFavorite(id: String): Boolean = favorites.contains(id)
}