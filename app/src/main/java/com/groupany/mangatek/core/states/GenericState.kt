package com.groupany.mangatek.core.states

import com.groupany.mangatek.core.domain.CustomFailure

sealed class GenericState<out T> {
    object Idle : GenericState<Nothing>()
    object Loading : GenericState<Nothing>()
    data class Success<T>(val value: T) : GenericState<T>()
    data class Failure(val failure: CustomFailure) : GenericState<Nothing>()

    fun isLoading(): Boolean = this is Loading
    fun isSuccess(): Boolean = this is Success
    fun isFailure(): Boolean = this is Failure

    val valueOrNull: T?
        get() = (this as? Success<T>)?.value

    val failureOrNull: CustomFailure?
        get() = (this as? Failure)?.failure
}