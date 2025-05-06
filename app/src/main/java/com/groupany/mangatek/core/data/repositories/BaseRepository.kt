package com.groupany.mangatek.core.data.repositories

import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.groupany.base.FailureType

open class BaseRepository {
    protected suspend fun <T> safeCall(
        call: suspend () -> T
    ): T {
        return try {
            return call()
        } catch (e: Exception) {
            throw  handleException(e)
        }
    }

    protected fun handleException(e: Exception): Exception {
        return when (e) {
            is FirebaseAuthInvalidUserException,
            is FirebaseAuthInvalidCredentialsException,
            is FirebaseAuthEmailException -> Exception(FailureType.InvalidCredential.key)
            is FirebaseNetworkException -> Exception(FailureType.NetworkError.key)
            is FirebaseTooManyRequestsException -> Exception(FailureType.TooManyRequests.key)
            is FirebaseException -> handleFirebaseException(e)
            else -> Exception("${e.javaClass.kotlin.simpleName} ${e.message}")
        }
    }

    private fun handleFirebaseException(fe: FirebaseException): Exception {
        if (fe.message?.contains("connect") == true) {
            return Exception(FailureType.NetworkError.key)
        }
        return Exception("${fe.javaClass.kotlin.simpleName} ${fe.message}")
    }
}