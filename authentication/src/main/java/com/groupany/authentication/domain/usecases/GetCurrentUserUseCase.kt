package com.groupany.authentication.domain.usecases

import com.groupany.authentication.domain.entities.UserEntity
import com.groupany.authentication.domain.repositories.AuthRepository
import com.groupany.base.CustomResult
import com.groupany.base.usecases.NoParamUseCase
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(private val repo: AuthRepository): NoParamUseCase<CustomResult<UserEntity>>() {
    override suspend fun invoke(): CustomResult<UserEntity> {
        return safeCall<UserEntity> {
            repo.getCurrentUser()
        }
    }
}