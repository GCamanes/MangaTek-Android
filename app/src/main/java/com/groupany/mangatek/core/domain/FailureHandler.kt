package com.groupany.mangatek.core.domain

import com.groupany.base.CustomFailure
import com.groupany.base.CustomResult
import com.groupany.base.FailureType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

open class FailureHandler {
    protected suspend fun <T> safeCall(
        call: suspend () -> T
    ): CustomResult<T> {
        return try {
            CustomResult.Success(call())
        } catch (e: Throwable) {
            CustomResult.Failure(handleException(e))
        }
    }

    protected fun <T> safeCallFlow(
        call: () -> Flow<T>
    ): Flow<CustomResult<T>> {
        return call()
            .map { result -> CustomResult.Success(result) as CustomResult<T> }
            .catch { e -> emit(CustomResult.Failure(handleException(e)).toFailure()) }
    }

    protected fun handleException(e: Throwable): CustomFailure {
        return when (e.message) {
            FailureType.InvalidCredential.key -> CustomFailure.InvalidCredential()
            FailureType.TooManyRequests.key -> CustomFailure.TooManyRequests()
            FailureType.NoUser.key -> CustomFailure.NoUser()
            FailureType.NetworkError.key -> CustomFailure.NetworkError()
            else -> CustomFailure.Unknown(e.message)
        }
    }
}