package com.groupany.mangatek.core.domain

sealed class CustomResult<out T> {
    data class Success<out T>(val value: T) : CustomResult<T>()
    data class Failure(val failure: CustomFailure) : CustomResult<Nothing>()

    val isSuccess: Boolean get() = this is Success
    val isFailure: Boolean get() = this is Failure

    fun getOrNull(): T? = (this as? Success<T>)?.value
    fun failureOrNull(): CustomFailure? = (this as? Failure)?.failure

    inline fun <R> fold(
        onSuccess: (T) -> R,
        onFailure: (CustomFailure) -> R
    ): R = when (this) {
        is Success -> onSuccess(value)
        is Failure -> onFailure(failure)
    }
}

