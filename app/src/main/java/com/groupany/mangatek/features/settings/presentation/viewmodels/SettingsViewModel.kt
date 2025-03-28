package com.groupany.mangatek.features.settings.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.groupany.mangatek.core.navigation.Screen
import com.groupany.mangatek.features.login.domain.usecases.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class SettingsViewModel @Inject constructor(private val logoutUseCase: LogoutUseCase) : ViewModel() {
    fun logoutUser(navController: NavHostController) {
        viewModelScope.launch {
            logoutUseCase()
            navController.navigate(Screen.Login.route) {
                popUpTo(0) { inclusive = true } // Clears the entire back stack
            }
        }
    }
}