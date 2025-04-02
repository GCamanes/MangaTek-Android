package com.groupany.mangatek.features.auth.domain.usecases

import com.groupany.mangatek.features.auth.domain.entities.UserEntity
import com.groupany.mangatek.features.auth.domain.repositories.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repo: AuthRepository) {
    suspend operator fun invoke(email: String, password: String) : Result<UserEntity> {
        return try {
            val user = repo.login(email, password)
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}