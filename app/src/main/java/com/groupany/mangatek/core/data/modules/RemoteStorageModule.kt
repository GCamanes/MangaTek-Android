package com.groupany.mangatek.core.data.modules

import com.groupany.mangatek.core.data.datasources.RemoteStorageDataSource
import com.groupany.mangatek.core.data.datasources.RemoteStorageDataSourceImpl
import com.groupany.mangatek.core.data.repositories.RemoteStorageRepositoryImpl
import com.groupany.mangatek.core.domain.repositories.RemoteStorageRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteStorageModule {
    @Provides
    @Singleton
    fun provideRemoteStorageDatasource(): RemoteStorageDataSource {
        return RemoteStorageDataSourceImpl()
    }

    @Provides
    @Singleton
    fun provideRemoteStorageRepository(
        remoteDatasource: RemoteStorageDataSource,
    ): RemoteStorageRepository {
        return RemoteStorageRepositoryImpl(remoteDatasource)
    }
}