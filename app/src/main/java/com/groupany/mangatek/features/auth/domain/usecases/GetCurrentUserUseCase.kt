package com.groupany.mangatek.features.auth.domain.usecases

import com.groupany.mangatek.features.auth.domain.entities.UserEntity
import com.groupany.mangatek.features.auth.domain.repositories.AuthRepository
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(private val repo: AuthRepository) {
    operator fun invoke() : Result<UserEntity?> {
        return try {
            val user = repo.getCurrentUser()
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}