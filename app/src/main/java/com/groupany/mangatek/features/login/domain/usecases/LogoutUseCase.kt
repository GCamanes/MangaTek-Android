package com.groupany.mangatek.features.login.domain.usecases

import com.groupany.mangatek.features.login.domain.repositories.LoginRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(private val repo: LoginRepository) {
    operator fun invoke() = repo.logout()
}