package com.groupany.mangatek.core.domain.usecases

import com.groupany.mangatek.core.domain.CustomFailure
import com.groupany.mangatek.core.domain.CustomResult
import com.groupany.mangatek.core.domain.FailureType

open class BaseUseCase {
    protected suspend fun <T> safeCall(
        call: suspend () -> T
    ): CustomResult<T> {
        return try {
            CustomResult.Success(call())
        } catch (e: Exception) {
            CustomResult.Failure(handleException(e))
        }
    }


    protected fun handleException(e: Exception): CustomFailure {
        return when (e.message) {
            FailureType.InvalidCredential.key -> CustomFailure.InvalidCredential()
            FailureType.TooManyRequests.key -> CustomFailure.TooManyRequests()
            FailureType.NoUser.key -> CustomFailure.NoUser()
            FailureType.NetworkError.key -> CustomFailure.NetworkError()
            else -> CustomFailure.Unknown(e.message)
        }
    }
}