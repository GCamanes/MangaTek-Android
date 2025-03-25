package com.groupany.mangatek.features.login.domain.repositories

import com.google.firebase.auth.FirebaseUser

interface LoginRepository {
     suspend fun login(email: String, password: String): FirebaseUser?
     fun logout()
}