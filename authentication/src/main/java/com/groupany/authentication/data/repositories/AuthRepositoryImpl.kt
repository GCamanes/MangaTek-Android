package com.groupany.authentication.data.repositories

import com.groupany.authentication.data.adapter.UserAdapter
import com.groupany.authentication.data.datasources.AuthDatasource
import com.groupany.authentication.domain.entities.UserEntity
import com.groupany.authentication.domain.repositories.AuthRepository
import com.groupany.base.repositories.BaseRepository
import com.groupany.base.FailureType

class AuthRepositoryImpl(private val datasource: AuthDatasource): AuthRepository, BaseRepository() {
    override suspend fun login(email: String, password: String): UserEntity {
        return safeCall {
            val user = datasource.login(email, password)
            UserAdapter.toUserEntity(user)
        }
    }

    override fun logout() = datasource.logout()

    override fun getCurrentUser(): UserEntity {
        val user = datasource.getCurrentUser()
        if (user != null) return UserAdapter.toUserEntity(user)
        else throw Exception(FailureType.NoUser.key)
    }
}