package com.groupany.mangatek.core.states

sealed class GenericState<out T> {
    object Idle : GenericState<Nothing>()
    object Loading : GenericState<Nothing>()
    data class Success<T>(val value: T) : GenericState<T>()
    data class Failure(val failure: String) : GenericState<Nothing>()

    fun isLoading(): Boolean = this is Loading
    fun isSuccess(): Boolean = this is Success
    fun isFailure(): Boolean = this is Failure

    val valueOrNull: T?
        get() = (this as? Success<T>)?.value

    val failureOrNull: String?
        get() = (this as? Failure)?.failure
}