package com.groupany.mangatek.features.home.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.groupany.base.CustomFailure
import com.groupany.base.CustomResult
import com.groupany.mangatek.core.domain.entities.MangaLightEntity
import com.groupany.mangatek.core.domain.usecases.GetDownloadedUrlUseCase
import com.groupany.mangatek.core.domain.usecases.GetFavoritesUseCase
import com.groupany.mangatek.core.domain.usecases.GetMangaListUseCase
import com.groupany.mangatek.core.domain.usecases.ToggleFavoriteUseCase
import com.groupany.mangatek.features.home.data.enums.HomeFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlin.collections.filter

@HiltViewModel
class HomeViewModel @Inject constructor(
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

    fun updateFilter(filter: HomeFilter) {
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
    val filter: HomeFilter = HomeFilter.ALL,
) {
    fun isFavorite(id: String) : Boolean = favorites.contains(id)

    private fun filterFavorites(id: String) : Boolean {
        return isFavorite(id)
    }

    fun getFilteredList() : List<MangaLightEntity> {
        return if (filter == HomeFilter.FAVORITES) mangaList.filter { it -> filterFavorites(it.id) }
            else mangaList
    }
}