package com.groupany.mangatek.features.login.data.datasources

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

class LoginDatasourceImpl(private val auth: FirebaseAuth) : LoginDatasource{
    override suspend fun login(email: String, password: String): FirebaseUser? {
        auth.signInWithEmailAndPassword(email, password).await()
        return auth.currentUser
    }

    override suspend fun logout() {
        return auth.signOut()
    }
}