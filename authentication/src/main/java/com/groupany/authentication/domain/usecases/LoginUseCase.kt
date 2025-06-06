package com.groupany.authentication.domain.usecases

import com.groupany.authentication.domain.entities.UserEntity
import com.groupany.authentication.domain.repositories.AuthRepository
import com.groupany.base.CustomResult
import com.groupany.base.usecases.UseCase
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repo: AuthRepository): UseCase<LoginParams, CustomResult<UserEntity>>() {
    override suspend fun invoke(input: LoginParams): CustomResult<UserEntity> {
        return safeCall<UserEntity> {
            repo.login(input.email, input.password)
        }
    }
}

class LoginParams(val email: String, val password: String)