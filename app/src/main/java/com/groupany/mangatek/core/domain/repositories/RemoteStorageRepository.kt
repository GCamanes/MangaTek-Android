package com.groupany.mangatek.core.domain.repositories

interface RemoteStorageRepository {
     suspend fun getDownloadUrl(path: String): String
}