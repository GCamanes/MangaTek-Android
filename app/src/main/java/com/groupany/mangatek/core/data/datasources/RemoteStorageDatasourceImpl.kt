package com.groupany.mangatek.core.data.datasources

import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

class RemoteStorageDataSourceImpl : RemoteStorageDataSource {
    override suspend fun getDownloadUrl(path: String): String {
        return FirebaseStorage.getInstance().reference.child(path).downloadUrl.await().toString()
    }
}