package com.groupany.mangatek.features.auth.data.repositories

import com.groupany.mangatek.features.auth.data.adapter.UserAdapter
import com.groupany.mangatek.features.auth.data.datasources.AuthDatasource
import com.groupany.mangatek.features.auth.domain.entities.UserEntity
import com.groupany.mangatek.features.auth.domain.repositories.AuthRepository

class AuthRepositoryImpl(private val datasource: AuthDatasource): AuthRepository {
    override suspend fun login(email: String, password: String): UserEntity {
        val user = datasource.login(email, password)
        return UserAdapter.toUserEntity(user)
    }

    override fun logout() = datasource.logout()

    override fun getCurrentUser(): UserEntity? {
        val user = datasource.getCurrentUser()
        return if (user != null) UserAdapter.toUserEntity(user) else null
    }
}