package com.groupany.manga.data.datasources

import com.google.firebase.firestore.FirebaseFirestore
import com.groupany.manga.data.models.MangaLightModel
import com.groupany.manga.data.models.MangaModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class MangaRemoteDataSourceImpl(private val firestore: FirebaseFirestore) : MangaRemoteDataSource {

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

    override fun getManga(id: String): Flow<MangaModel> = callbackFlow {
        val mangaDocRef = firestore.collection("mangas").document(id)

        val listener = mangaDocRef.addSnapshotListener { snapshot, error ->
            if (error != null) {
                // Optional: handle error
                close(error)
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                val manga = snapshot.toObject(MangaModel::class.java)?.copy(id = snapshot.id)
                if (manga != null) trySend(manga).isSuccess
            }
        }

        awaitClose { listener.remove() }
    }

}