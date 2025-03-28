package com.groupany.mangatek.features.login.domain.usecases

import com.groupany.mangatek.features.login.domain.entities.UserEntity
import com.groupany.mangatek.features.login.domain.repositories.LoginRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repo: LoginRepository) {
    suspend operator fun invoke(email: String, password: String) : Result<UserEntity> {
        return try {
            val user = repo.login(email, password)
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}