package com.groupany.mangatek.features.login.domain.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.groupany.mangatek.features.login.domain.repositories.LoginRepository
import com.groupany.mangatek.features.login.domain.usecases.LoginUseCase
import com.groupany.mangatek.features.login.domain.usecases.LogoutUseCase

@Module
@InstallIn(SingletonComponent::class)
object LoginDomainModule {
    @Provides
    @Singleton
    fun provideLoginUseCase(repo: LoginRepository): LoginUseCase {
        return LoginUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideLogoutUseCase(repo: LoginRepository): LogoutUseCase {
        return LogoutUseCase(repo)
    }
}