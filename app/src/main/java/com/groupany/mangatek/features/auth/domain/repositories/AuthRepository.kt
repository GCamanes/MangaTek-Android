package com.groupany.mangatek.features.auth.domain.repositories

import com.groupany.mangatek.features.auth.domain.entities.UserEntity

interface AuthRepository {
     suspend fun login(email: String, password: String): UserEntity
     fun logout()
     fun getCurrentUser(): UserEntity
}