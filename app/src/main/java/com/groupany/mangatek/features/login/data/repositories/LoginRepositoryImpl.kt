package com.groupany.mangatek.features.login.data.repositories

import com.google.firebase.auth.FirebaseUser
import com.groupany.mangatek.features.login.data.datasources.LoginDatasource
import com.groupany.mangatek.features.login.domain.repositories.LoginRepository

class LoginRepositoryImpl(private val datasource: LoginDatasource): LoginRepository {
    override suspend fun login(email: String, password: String): FirebaseUser? {
        return datasource.login(email, password)
    }

    override fun logout() = datasource.logout()
}