package com.groupany.authentication.data.modules

import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.groupany.authentication.data.datasources.AuthDatasource
import com.groupany.authentication.data.datasources.AuthDatasourceImpl
import com.groupany.authentication.data.repositories.AuthRepositoryImpl
import com.groupany.authentication.domain.repositories.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {
    @Provides
    @Singleton
    fun provideLoginDatasource(): AuthDatasource {
        return AuthDatasourceImpl(Firebase.auth)
    }

    @Provides
    @Singleton
    fun provideLoginRepository(datasource: AuthDatasource): AuthRepository {
        return AuthRepositoryImpl(datasource)
    }
}