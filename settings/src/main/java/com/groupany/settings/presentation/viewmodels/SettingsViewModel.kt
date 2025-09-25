package com.groupany.settings.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.groupany.authentication.domain.usecases.LogoutUseCase
import com.groupany.base.usecases.GetAppVersionUseCase
import com.groupany.manga.domain.usecases.ClearAllFavoritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val getAppVersionUseCase: GetAppVersionUseCase,
    private val clearAllFavoritesUseCase: ClearAllFavoritesUseCase,
) : ViewModel() {
    private val _appVersion = MutableStateFlow("")
    val appVersion: StateFlow<String> = _appVersion.asStateFlow()

    init {
        viewModelScope.launch { _appVersion.value = getAppVersionUseCase() }
    }

    fun logoutUser() {
        viewModelScope.launch {
            clearAllFavoritesUseCase()
            logoutUseCase()
        }
    }
}