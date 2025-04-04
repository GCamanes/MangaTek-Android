package com.groupany.mangatek.features.auth.domain.usecases

import com.groupany.mangatek.core.domain.CustomResult
import com.groupany.mangatek.core.errors.CustomException
import com.groupany.mangatek.features.auth.domain.entities.UserEntity
import com.groupany.mangatek.features.auth.domain.repositories.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repo: AuthRepository) {
    suspend operator fun invoke(email: String, password: String) : CustomResult<UserEntity> {
        return try {
            val user = repo.login(email, password)
            CustomResult.Success(user)
        } catch (e: Exception) {
            when(e) {
                is CustomException -> CustomResult.Failure(e)
                else -> CustomResult.Failure(CustomException.Unknown(e.message))
            }
        }
    }
}