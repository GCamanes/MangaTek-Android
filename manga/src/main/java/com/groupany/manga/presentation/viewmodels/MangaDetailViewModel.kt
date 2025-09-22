package com.groupany.manga.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.groupany.base.CustomFailure
import com.groupany.manga.domain.entities.MangaLightEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MangaDetailViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(MangaDetailUiState())
    val uiState: StateFlow<MangaDetailUiState> = _uiState
}

data class MangaDetailUiState(
    val mangaList: List<MangaLightEntity> = emptyList(),
    val failure: CustomFailure? = null,
    val isLoading: Boolean = false,
) {}