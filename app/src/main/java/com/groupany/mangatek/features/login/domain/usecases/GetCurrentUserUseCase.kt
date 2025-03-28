package com.groupany.mangatek.features.login.domain.usecases

import com.groupany.mangatek.features.login.domain.entities.UserEntity
import com.groupany.mangatek.features.login.domain.repositories.LoginRepository
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(private val repo: LoginRepository) {
    operator fun invoke() : Result<UserEntity?> {
        return try {
            val user = repo.getCurrentUser()
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}