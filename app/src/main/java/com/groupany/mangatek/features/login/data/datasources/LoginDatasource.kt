package com.groupany.mangatek.features.login.data.datasources

import com.google.firebase.auth.FirebaseUser

interface LoginDatasource {
    suspend fun login(email: String, password: String): FirebaseUser
    fun logout()
    fun getCurrentUser(): FirebaseUser?
}