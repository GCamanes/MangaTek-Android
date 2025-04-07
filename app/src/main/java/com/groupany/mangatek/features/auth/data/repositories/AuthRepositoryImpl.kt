package com.groupany.mangatek.features.auth.data.repositories

import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuthException
import com.groupany.mangatek.core.data.repositories.BaseRepository
import com.groupany.mangatek.core.domain.FailureType
import com.groupany.mangatek.features.auth.data.adapter.UserAdapter
import com.groupany.mangatek.features.auth.data.datasources.AuthDatasource
import com.groupany.mangatek.features.auth.domain.entities.UserEntity
import com.groupany.mangatek.features.auth.domain.repositories.AuthRepository

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