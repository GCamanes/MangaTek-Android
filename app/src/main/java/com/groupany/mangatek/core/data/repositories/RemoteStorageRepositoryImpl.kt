package com.groupany.mangatek.core.data.repositories

import com.groupany.base.repositories.BaseRepository
import com.groupany.mangatek.core.data.datasources.RemoteStorageDataSource
import com.groupany.mangatek.core.domain.repositories.RemoteStorageRepository

class RemoteStorageRepositoryImpl(
    private val remoteDataSource: RemoteStorageDataSource,
) : RemoteStorageRepository, BaseRepository() {
    override suspend fun getDownloadUrl(path: String): String {
        return safeCall {
            remoteDataSource.getDownloadUrl(path)
        }
    }
}