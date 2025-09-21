package com.groupany.base.validators

import android.util.Patterns
import kotlin.text.isBlank

class EmailValidator {
    operator fun invoke(email: String): EmailValidationResult {
        return if (email.isBlank()) EmailValidationResult.IS_EMPTY
        else if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            EmailValidationResult.CORRECT
        }
        else EmailValidationResult.INCORRECT_FORMAT
    }
}

class PasswordValidator {
    operator fun invoke(password: String): PasswordValidationResult {
        return if (password.isBlank()) PasswordValidationResult.IS_EMPTY
        else PasswordValidationResult.CORRECT
    }
}

enum class EmailValidationResult {
    IS_EMPTY,
    INCORRECT_FORMAT,
    CORRECT
}
enum class PasswordValidationResult {
    IS_EMPTY,
    CORRECT
}