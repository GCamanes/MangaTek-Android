package com.groupany.mangatek.features.login.domain.usecases

import com.groupany.mangatek.features.login.domain.repositories.LoginRepository

class LogoutUseCase(private val repo: LoginRepository) {
    suspend fun execute() = repo.logout()
}