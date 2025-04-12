package com.groupany.mangatek.core.data.datasources

import com.google.firebase.firestore.FirebaseFirestore
import com.groupany.mangatek.core.data.models.MangaLightModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class MangaRemoteDataSourceImpl(
    private val firestore: FirebaseFirestore
) : MangaRemoteDataSource {

    private val docRef = firestore.collection("mangas-list").document("mangas-list")

    override fun getMangaList(): Flow<List<MangaLightModel>> = callbackFlow {
        val listener = docRef.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }

            val rawList = snapshot?.get("mangas")
            val mangas = if (rawList is List<*>) {
                rawList.mapNotNull { item ->
                    if (item is Map<*, *>) {
                        @Suppress("UNCHECKED_CAST")
                        MangaLightModel.fromMap(item as Map<String, Any>)
                    } else null
                }
            } else emptyList()
            trySend(mangas)
        }
        awaitClose { listener.remove() }
    }
}