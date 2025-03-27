package com.groupany.mangatek.features.login.domain.usecases

import com.google.firebase.auth.FirebaseUser
import com.groupany.mangatek.features.login.domain.repositories.LoginRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repo: LoginRepository) {
    suspend fun execute(email: String, password: String) : Result<FirebaseUser> {
        return try {
            val user = repo.login(email, password)
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}