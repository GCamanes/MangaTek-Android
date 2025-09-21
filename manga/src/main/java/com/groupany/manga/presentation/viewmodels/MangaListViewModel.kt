package com.groupany.manga.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.groupany.base.CustomFailure
import com.groupany.base.CustomResult
import com.groupany.firebase.domain.usecases.GetDownloadedUrlUseCase
import com.groupany.manga.domain.entities.MangaLightEntity
import com.groupany.manga.domain.enums.MangaFilter
import com.groupany.manga.domain.usecases.GetFavoritesUseCase
import com.groupany.manga.domain.usecases.GetMangaListUseCase
import com.groupany.manga.domain.usecases.ToggleFavoriteUseCase
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
    private val getDownloadedUrlUseCase: GetDownloadedUrlUseCase,
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(MangaListUiState())
    val uiState: StateFlow<MangaListUiState> = _uiState

    private val imageUrlCache = mutableMapOf<String, String?>()

    init {
        loadUiState()
    }

    fun loadUiState() {
        viewModelScope.launch {
            _uiState.update { it.copy(favorites = getFavoritesUseCase()) }
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
    }

    fun toggleFavorite(id: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(favorites = toggleFavoriteUseCase(id)) }
        }
    }

    fun updateFilter(filter: MangaFilter) {
        viewModelScope.launch {
            _uiState.update { it.copy(filter = filter) }
        }
    }

    fun getCachedUrl(path: String): String? {
        return imageUrlCache[path]
    }

    suspend fun getDownloadUrl(path: String): String? {
        return imageUrlCache[path] ?: run {
            val url = getDownloadedUrlUseCase(path).getOrNull()
            imageUrlCache[path] = url
            url
        }
    }
}

data class MangaListUiState(
    val mangaList: List<MangaLightEntity> = emptyList(),
    val favorites: Set<String> = emptySet<String>(),
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