package com.groupany.mangatek.features.settings.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.groupany.mangatek.core.domain.usecases.GetAppVersionUseCase
import com.groupany.mangatek.features.auth.domain.usecases.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    getAppVersionUseCase: GetAppVersionUseCase
) : ViewModel() {
    // Version
    private val _appVersion = MutableStateFlow("")
    val appVersion: StateFlow<String> = _appVersion.asStateFlow()

    init {
        viewModelScope.launch { _appVersion.value = getAppVersionUseCase() }
    }

    fun logoutUser() {
        viewModelScope.launch {
            logoutUseCase()
        }
    }
}