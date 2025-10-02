package com.groupany.manga.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.groupany.base.CustomFailure
import com.groupany.base.CustomResult
import com.groupany.manga.domain.entities.MangaLightEntity
import com.groupany.manga.domain.enums.MangaFilter
import com.groupany.manga.domain.usecases.GetAllFavoritesUseCase
import com.groupany.manga.domain.usecases.GetMangaCoverParams
import com.groupany.manga.domain.usecases.GetMangaCoverUseCase
import com.groupany.manga.domain.usecases.GetMangaListUseCase
import com.groupany.manga.domain.usecases.ToggleAFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MangaListViewModel @Inject constructor(
    private val getMangaListUseCase: GetMangaListUseCase,
    private val getMangaCoverUseCase: GetMangaCoverUseCase,
    private val getAllFavoritesUseCase: GetAllFavoritesUseCase,
    private val toggleAFavoriteUseCase: ToggleAFavoriteUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(MangaListUiState())
    val uiState: StateFlow<MangaListUiState> = _uiState

    init {
        loadUiState()
    }

    fun loadUiState() {
        viewModelScope.launch {
            getMangaListUseCase()
                .onStart {
                    _uiState.update { it.copy(isLoading = true, failure = null) }
                }
                .collect { result ->
                    _uiState.update {
                        when (result) {
                            is CustomResult.Success -> it.copy(
                                mangaList = result.value,
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

    fun updateFilter(filter: MangaFilter) {
        viewModelScope.launch {
            _uiState.update { it.copy(filter = filter) }
        }
    }

    suspend fun getCoverUrl(id: String, path: String): String? {
        val cachedUrl = getMangaCoverUseCase(GetMangaCoverParams(id, path))
        return cachedUrl.getOrNull()?.cachedUrl
    }
}

data class MangaListUiState(
    val mangaList: List<MangaLightEntity> = emptyList(),
    val favorites: Set<String> = emptySet(),
    val failure: CustomFailure? = null,
    val isLoading: Boolean = false,
    val filter: MangaFilter = MangaFilter.ALL,
) {
    fun isFavorite(id: String) : Boolean = favorites.contains(id)

    private fun filterFavorites(id: String) : Boolean {
        return isFavorite(id)
    }

    fun getFilteredList() : List<MangaLightEntity> {
        return if (filter == MangaFilter.FAVORITES) mangaList.filter { it -> filterFavorites(it.id) }
            else mangaList
    }
}