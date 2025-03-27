package com.groupany.mangatek.features.login.domain.repositories

import com.groupany.mangatek.features.login.domain.entities.UserEntity

interface LoginRepository {
     suspend fun login(email: String, password: String): UserEntity
     fun logout()
     fun getCurrentUser(): UserEntity?
}