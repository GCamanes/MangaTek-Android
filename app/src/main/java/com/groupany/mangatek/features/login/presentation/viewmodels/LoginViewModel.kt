package com.groupany.mangatek.features.login.presentation.viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    private val _userName = MutableStateFlow("")
    val userName: StateFlow<String> = _userName

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    fun onUserNameChange(newValue: String) {
        _userName.value = newValue
    }

    fun onPasswordChange(newValue: String) {
        _password.value = newValue
    }
}