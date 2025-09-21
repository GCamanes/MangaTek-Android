package com.groupany.authentication.data.datasources

import com.google.firebase.auth.FirebaseUser

interface AuthDatasource {
    suspend fun login(email: String, password: String): FirebaseUser
    fun logout()
    fun getCurrentUser(): FirebaseUser?
}