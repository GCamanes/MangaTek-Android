package com.groupany.mangatek.features.home.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.groupany.mangatek.core.domain.CustomFailure
import com.groupany.mangatek.core.domain.CustomResult
import com.groupany.mangatek.core.domain.entities.MangaLightEntity
import com.groupany.mangatek.core.domain.usecases.GetMangaListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMangaListUseCase: GetMangaListUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(MangaListUiState())
    val uiState: StateFlow<MangaListUiState> = _uiState

    init {
        loadManga()
    }

    fun loadManga() {
        viewModelScope.launch {
            getMangaListUseCase()
                .onStart {
                    _uiState.update { it.copy(isLoading = true, error = null) }
                }
                .collect { result ->
                    _uiState.update {
                        when (result) {
                            is CustomResult.Success -> it.copy(
                                mangaList = result.value,
                                isLoading = false,
                                error = null
                            )
                            is CustomResult.Failure -> it.copy(
                                error = result.failure,
                                isLoading = false
                            )
                        }
                    }
                }
        }
    }
}

data class MangaListUiState(
    val mangaList: List<MangaLightEntity> = emptyList(),
    val error: CustomFailure? = null,
    val isLoading: Boolean = false
)