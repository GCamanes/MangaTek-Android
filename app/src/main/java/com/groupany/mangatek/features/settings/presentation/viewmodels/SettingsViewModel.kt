package com.groupany.mangatek.features.settings.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.groupany.mangatek.core.domain.usecases.GetAppVersionUseCase
import com.groupany.mangatek.core.helpers.NavHelper
import com.groupany.mangatek.core.navigation.Screen
import com.groupany.mangatek.features.auth.domain.usecases.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    getAppVersionUseCase: GetAppVersionUseCase
) : ViewModel() {
    val appVersion = getAppVersionUseCase()

    fun logoutUser() {
        viewModelScope.launch {
            logoutUseCase()
        }
    }
}