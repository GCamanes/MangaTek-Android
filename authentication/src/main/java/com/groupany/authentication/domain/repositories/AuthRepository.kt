package com.groupany.authentication.domain.repositories

import com.groupany.authentication.domain.entities.UserEntity

interface AuthRepository {
     suspend fun login(email: String, password: String): UserEntity
     fun logout()
     fun getCurrentUser(): UserEntity
}