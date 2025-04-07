package com.groupany.mangatek.features.auth.domain.usecases

import com.groupany.mangatek.core.domain.CustomResult
import com.groupany.mangatek.core.domain.usecases.BaseUseCase
import com.groupany.mangatek.features.auth.domain.entities.UserEntity
import com.groupany.mangatek.features.auth.domain.repositories.AuthRepository
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(private val repo: AuthRepository) : BaseUseCase() {
    suspend operator fun invoke() : CustomResult<UserEntity> {
        return safeCall<UserEntity> {
            repo.getCurrentUser()
        }
    }
}