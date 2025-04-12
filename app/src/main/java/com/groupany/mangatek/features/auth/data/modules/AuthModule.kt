package com.groupany.mangatek.features.auth.data.modules

import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.groupany.mangatek.features.auth.data.datasources.AuthDatasource
import com.groupany.mangatek.features.auth.data.datasources.AuthDatasourceImpl
import com.groupany.mangatek.features.auth.domain.repositories.AuthRepository
import com.groupany.mangatek.features.auth.data.repositories.AuthRepositoryImpl

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