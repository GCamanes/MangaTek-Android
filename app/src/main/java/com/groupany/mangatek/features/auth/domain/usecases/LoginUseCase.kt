package com.groupany.mangatek.features.auth.domain.usecases

import com.groupany.mangatek.core.domain.CustomResult
import com.groupany.mangatek.core.domain.usecases.UseCase
import com.groupany.mangatek.features.auth.domain.entities.UserEntity
import com.groupany.mangatek.features.auth.domain.repositories.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repo: AuthRepository): UseCase<LoginParams, CustomResult<UserEntity>>() {
    override suspend fun invoke(input: LoginParams): CustomResult<UserEntity> {
        return safeCall<UserEntity> {
            repo.login(input.email, input.password)
        }
    }
}

class LoginParams(val email: String, val password: String)