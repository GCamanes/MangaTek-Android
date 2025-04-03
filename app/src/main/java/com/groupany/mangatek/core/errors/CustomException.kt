package com.groupany.mangatek.core.errors

sealed class CustomException(message: String?) : Exception(message) {
    class InvalidCredential(message: String?) : CustomException(message)
    class NoUser() : CustomException("No user")
    class TooManyRequests(message: String?) : CustomException(message)
    class NetworkError(message: String?) : CustomException(message)
    class Unknown(message: String?) : CustomException(message)

    override fun toString(): String {
        return "${this.javaClass.kotlin.simpleName}: $message"
    }
}

