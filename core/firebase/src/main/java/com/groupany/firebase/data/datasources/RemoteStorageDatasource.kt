package com.groupany.firebase.data.datasources

interface RemoteStorageDataSource {
    suspend fun getDownloadUrl(path: String): String
}