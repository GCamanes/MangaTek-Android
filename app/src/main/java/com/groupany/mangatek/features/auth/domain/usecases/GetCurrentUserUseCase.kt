package com.groupany.mangatek.features.auth.domain.usecases

import com.groupany.mangatek.core.errors.CustomException
import com.groupany.mangatek.core.domain.CustomResult
import com.groupany.mangatek.features.auth.domain.entities.UserEntity
import com.groupany.mangatek.features.auth.domain.repositories.AuthRepository
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(private val repo: AuthRepository) {
    operator fun invoke() : CustomResult<UserEntity> {
        return try {
            val user = repo.getCurrentUser()
            CustomResult.Success(user)
        } catch (e: Exception) {
            when(e) {
                is CustomException -> CustomResult.Failure(e)
                else -> CustomResult.Failure(CustomException.Unknown(e.message))
            }
        }
    }
}