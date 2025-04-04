package com.groupany.mangatek.core.domain

import com.groupany.mangatek.core.errors.CustomException

sealed class CustomResult<out T> {
    data class Success<out T>(val value: T) : CustomResult<T>()
    data class Failure(val failure: CustomException) : CustomResult<Nothing>()

    val isSuccess: Boolean get() = this is Success
    val isFailure: Boolean get() = this is Failure

    fun getOrNull(): T? = (this as? Success<T>)?.value
    fun exceptionOrNull(): CustomException? = (this as? Failure)?.failure

    inline fun <R> fold(
        onSuccess: (T) -> R,
        onFailure: (CustomException) -> R
    ): R = when (this) {
        is Success -> onSuccess(value)
        is Failure -> onFailure(failure)
    }
}

