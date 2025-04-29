package com.groupany.mangatek.core.domain

import android.content.Context
import com.groupany.localization.R

enum class FailureType (val key: String) {
     InvalidCredential("INVALID_CREDENTIALS"),
     NoUser("NO_USER"),
     TooManyRequests("TOO_MANY_REQUESTS"),
     NetworkError("NETWORK_ERROR"),
}

sealed class CustomFailure(message: String?) : Exception(message) {
    class InvalidCredential() : CustomFailure(FailureType.InvalidCredential.key)
    class NoUser() : CustomFailure(FailureType.NoUser.key)
    class TooManyRequests() : CustomFailure(FailureType.TooManyRequests.key)
    class NetworkError() : CustomFailure(FailureType.NetworkError.key)
    class Unknown(message: String?) : CustomFailure(message)

    override fun toString(): String {
        return "${this.javaClass.kotlin.simpleName}: $message"
    }

    fun trad(context: Context): String {
        return when(this) {
            is InvalidCredential -> context.getString(R.string.error_credentials)
            is NetworkError -> context.getString(R.string.error_network)
            is TooManyRequests -> context.getString(R.string.error_too_many_request)
            else -> context.getString(R.string.error_unknown)
        }
    }
}