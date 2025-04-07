package com.groupany.mangatek.features.auth.presentation.viewmodels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.groupany.mangatek.BuildConfig
import com.groupany.mangatek.core.domain.usecases.GetAppVersionUseCase
import com.groupany.mangatek.core.states.GenericState
import com.groupany.mangatek.core.validators.EmailValidationResult
import com.groupany.mangatek.core.validators.EmailValidator
import com.groupany.mangatek.core.validators.PasswordValidationResult
import com.groupany.mangatek.core.validators.PasswordValidator
import com.groupany.mangatek.features.auth.domain.entities.UserEntity
import com.groupany.mangatek.features.auth.domain.usecases.GetCurrentUserUseCase
import com.groupany.mangatek.features.auth.domain.usecases.LoginParams
import com.groupany.mangatek.features.auth.domain.usecases.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    getAppVersionUseCase: GetAppVersionUseCase
) : ViewModel() {
    // State of login / current user
    private val _loginState = MutableStateFlow<GenericState<UserEntity>>(GenericState.Idle)
    val loginState: StateFlow<GenericState<UserEntity>> = _loginState.asStateFlow()
    private val _currentUserState = MutableStateFlow<GenericState<UserEntity>>(GenericState.Idle)
    val currentUserState: StateFlow<GenericState<UserEntity>> = _currentUserState.asStateFlow()

    // Follow state for login form
    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email
    private val emailValidator = EmailValidator()
    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password
    private val passwordValidator = PasswordValidator()
    private val _validationState = MutableStateFlow(ValidationState())
    val validationState = _validationState.asStateFlow()

    // Version
    private val _appVersion = MutableStateFlow("")
    val appVersion: StateFlow<String> = _appVersion.asStateFlow()

    init {
        // Retrieve values from local properties
        onEmailChange(BuildConfig.EMAIL)
        onPasswordChange(BuildConfig.PASSWORD)
        viewModelScope.launch { _appVersion.value = getAppVersionUseCase() }
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
            val result = loginUseCase(LoginParams(email, password))
            _loginState.value = result.fold(
                onSuccess = { user -> GenericState.Success(user) },
                onFailure = { error -> GenericState.Failure(error.toString()) }
            )
        }
    }

    fun getCurrentUser() {
        viewModelScope.launch {
            _currentUserState.value = GenericState.Loading
            val result = getCurrentUserUseCase()
            delay(1000)
            _currentUserState.value = result.fold(
                onSuccess = { user -> GenericState.Success(user) },
                onFailure = { error -> GenericState.Failure(error.message ?: "Unknown error") }
            )
        }
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