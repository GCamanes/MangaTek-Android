package com.groupany.mangatek.core.states

sealed class GenericState<out T> {
    object Idle : GenericState<Nothing>()
    object Loading : GenericState<Nothing>()
    data class Success<T>(val data: T) : GenericState<T>()
    data class Error(val message: String) : GenericState<Nothing>()
}