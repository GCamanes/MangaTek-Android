package com.groupany.mangatek.features.login.data.modules

import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.groupany.mangatek.features.login.data.datasources.LoginDatasource
import com.groupany.mangatek.features.login.data.datasources.LoginDatasourceImpl
import com.groupany.mangatek.features.login.domain.repositories.LoginRepository
import com.groupany.mangatek.features.login.data.repositories.LoginRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
object LoginDataModule {
    @Provides
    @Singleton
    fun provideLoginDatasource(): LoginDatasource {
        return LoginDatasourceImpl(Firebase.auth)
    }

    @Provides
    @Singleton
    fun provideLoginRepository(datasource: LoginDatasource): LoginRepository {
        return LoginRepositoryImpl(datasource)
    }
}