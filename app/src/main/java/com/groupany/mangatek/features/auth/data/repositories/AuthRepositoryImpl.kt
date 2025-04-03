package com.groupany.mangatek.features.auth.data.repositories

import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuthException
import com.groupany.mangatek.core.errors.CustomException
import com.groupany.mangatek.features.auth.data.adapter.UserAdapter
import com.groupany.mangatek.features.auth.data.datasources.AuthDatasource
import com.groupany.mangatek.features.auth.domain.entities.UserEntity
import com.groupany.mangatek.features.auth.domain.repositories.AuthRepository

class AuthRepositoryImpl(private val datasource: AuthDatasource): AuthRepository {
    override suspend fun login(email: String, password: String): UserEntity {
        return try {
            val user = datasource.login(email, password)
            return UserAdapter.toUserEntity(user)
        } catch (e: FirebaseAuthException) {
            throw when (e.javaClass.kotlin.simpleName) {
                "FirebaseAuthInvalidUserException", "FirebaseAuthInvalidCredentialsException", "FirebaseAuthEmailException"
                    -> CustomException.InvalidCredential(e.errorCode)
                "ERROR_TOO_MANY_REQUESTS" -> CustomException.TooManyRequests(e.errorCode)
                else -> CustomException.Unknown(e.errorCode)
            }
        } catch (e: FirebaseException) {
            throw when (e.javaClass.kotlin.simpleName) {
                "FirebaseNetworkException" -> CustomException.NetworkError(e.message)
                "FirebaseTooManyRequestsException" -> CustomException.TooManyRequests(e.message)
                else -> CustomException.Unknown(e.message)
            }
        } catch (e: Exception) {
            throw CustomException.Unknown(e.message ?: "Unknown")
        }
    }

    override fun logout() = datasource.logout()

    override fun getCurrentUser(): UserEntity {
        val user = datasource.getCurrentUser()
        if (user != null) return UserAdapter.toUserEntity(user)
        else throw CustomException.NoUser()
    }
}