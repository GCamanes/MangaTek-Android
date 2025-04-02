package com.groupany.mangatek.features.auth.data.datasources

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

class AuthDatasourceImpl(private val auth: FirebaseAuth) : AuthDatasource{
    override suspend fun login(email: String, password: String): FirebaseUser {
        auth.signInWithEmailAndPassword(email, password).await()
        return auth.currentUser!!
    }

    override fun logout() = auth.signOut()

    override fun getCurrentUser(): FirebaseUser? = auth.currentUser
}