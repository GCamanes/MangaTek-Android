package com.groupany.mangatek.features.login.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.groupany.mangatek.core.states.GenericState
import com.groupany.mangatek.features.login.domain.usecases.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase) : ViewModel() {

    private val _loginState = MutableStateFlow<GenericState<FirebaseUser?>>(GenericState.Idle)
    val loginState: StateFlow<GenericState<FirebaseUser?>> = _loginState.asStateFlow()

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    fun onUserNameChange(newValue: String) {
        _email.value = newValue
    }

    fun onPasswordChange(newValue: String) {
        _password.value = newValue
    }

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = GenericState.Loading
            val result = loginUseCase.execute(email, password)
            _loginState.value = result.fold(
                onSuccess = { user -> GenericState.Success(user) },
                onFailure = { error -> GenericState.Error(error.message ?: "Unknown error") }
            )
        }
    }
}