package com.groupany.firebase.data.modules

import com.groupany.firebase.data.datasources.RemoteStorageDataSource
import com.groupany.firebase.data.datasources.RemoteStorageDataSourceImpl
import com.groupany.firebase.data.repositories.RemoteStorageRepositoryImpl
import com.groupany.firebase.domain.repositories.RemoteStorageRepository
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