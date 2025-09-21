package com.groupany.mangatek.features.settings.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.groupany.authentication.domain.usecases.LogoutUseCase
import com.groupany.base.usecases.GetAppVersionUseCase
import com.groupany.manga.domain.usecases.ClearFavoritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val clearFavoritesUseCase: ClearFavoritesUseCase,
    private val getAppVersionUseCase: GetAppVersionUseCase
) : ViewModel() {
    private val _appVersion = MutableStateFlow("")
    val appVersion: StateFlow<String> = _appVersion.asStateFlow()

    init {
        viewModelScope.launch { _appVersion.value = getAppVersionUseCase() }
    }

    fun logoutUser() {
        viewModelScope.launch {
            clearFavoritesUseCase()
            logoutUseCase()
        }
    }
}