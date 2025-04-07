package com.groupany.mangatek.features.auth.domain.usecases

import com.groupany.mangatek.core.domain.usecases.BlankUseCase
import com.groupany.mangatek.features.auth.domain.repositories.AuthRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(private val repo: AuthRepository) : BlankUseCase() {
    override suspend fun invoke() = repo.logout()
}