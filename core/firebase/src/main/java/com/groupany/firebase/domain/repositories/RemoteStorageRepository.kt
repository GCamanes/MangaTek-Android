package com.groupany.firebase.domain.repositories

interface RemoteStorageRepository {
     suspend fun getDownloadUrl(path: String): String
}