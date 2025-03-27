package com.groupany.mangatek.features.login.data.repositories

import com.groupany.mangatek.features.login.data.adapter.UserAdapter
import com.groupany.mangatek.features.login.data.datasources.LoginDatasource
import com.groupany.mangatek.features.login.domain.entities.UserEntity
import com.groupany.mangatek.features.login.domain.repositories.LoginRepository

class LoginRepositoryImpl(private val datasource: LoginDatasource): LoginRepository {
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