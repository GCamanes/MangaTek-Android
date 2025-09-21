package com.groupany.firebase.data.repositories

import com.groupany.base.repositories.BaseRepository
import com.groupany.firebase.data.datasources.RemoteStorageDataSource
import com.groupany.firebase.domain.repositories.RemoteStorageRepository

class RemoteStorageRepositoryImpl(
    private val remoteDataSource: RemoteStorageDataSource,
) : RemoteStorageRepository, BaseRepository() {
    override suspend fun getDownloadUrl(path: String): String {
        return safeCall {
            remoteDataSource.getDownloadUrl(path)
        }
    }
}