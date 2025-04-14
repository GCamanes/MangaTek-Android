package com.groupany.mangatek.core.data.datasources

interface RemoteStorageDataSource {
    suspend fun getDownloadUrl(path: String): String
}