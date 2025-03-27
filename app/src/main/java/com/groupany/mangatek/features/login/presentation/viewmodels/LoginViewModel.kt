package com.groupany.mangatek.features.login.presentation.viewmodels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.groupany.mangatek.core.helpers.PropertyHelper
import com.groupany.mangatek.core.states.GenericState
import com.groupany.mangatek.core.validators.EmailValidationResult
import com.groupany.mangatek.core.validators.EmailValidator
import com.groupany.mangatek.core.validators.PasswordValidationResult
import com.groupany.mangatek.core.validators.PasswordValidator
import com.groupany.mangatek.features.login.domain.entities.UserEntity
import com.groupany.mangatek.features.login.domain.usecases.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase) : ViewModel() {
    // Follow state of login
    private val _loginState = MutableStateFlow<GenericState<UserEntity>>(GenericState.Idle)
    val loginState: StateFlow<GenericState<UserEntity>> = _loginState.asStateFlow()

    // Follow state for login form
    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email
    private val emailValidator = EmailValidator()
    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password
    private val passwordValidator = PasswordValidator()
    private val _validationState = MutableStateFlow(ValidationState())
    val validationState = _validationState.asStateFlow()

    init {
        // Retrieve values from local properties
        onEmailChange(PropertyHelper.getEmailProperty())
        onPasswordChange(PropertyHelper.getPasswordProperty())
    }

    fun onEmailChange(newValue: String) {
        _email.value = newValue
        _validationState.update { it.copy(emailValidation = emailValidator(newValue)) }
    }

    fun onPasswordChange(newValue: String) {
        _password.value = newValue
        _validationState.update { it.copy(passwordValidation = passwordValidator(newValue)) }
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

    fun resetLoginState() {
        _loginState.value = GenericState.Idle
    }
}

data class ValidationState(
    val emailValidation: EmailValidationResult = EmailValidationResult.IS_EMPTY,
    val passwordValidation: PasswordValidationResult = PasswordValidationResult.IS_EMPTY,
) {
    fun isValid(): Boolean {
        return emailValidation == EmailValidationResult.CORRECT
                && passwordValidation == PasswordValidationResult.CORRECT
    }
}