package com.groupany.authentication.domain.usecases

import com.groupany.authentication.domain.repositories.AuthRepository
import com.groupany.base.usecases.BlankUseCase
import javax.inject.Inject

class LogoutUseCase @Inject constructor(private val repo: AuthRepository) : BlankUseCase() {
    override suspend fun invoke() = repo.logout()
}