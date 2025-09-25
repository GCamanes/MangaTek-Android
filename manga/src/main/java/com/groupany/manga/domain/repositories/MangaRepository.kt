package com.groupany.manga.domain.repositories

import com.groupany.manga.domain.entities.MangaLightEntity
import kotlinx.coroutines.flow.Flow

interface MangaRepository {
    fun getMangaList(): Flow<List<MangaLightEntity>>
    fun getFavorites(): Set<String>
    fun toggleFavorite(id: String): Set<String>
    fun clearFavorites()

    fun getAllFavorites(): Flow<List<String>>
    fun isFavorite(id: String): Flow<Boolean>
    suspend fun addFavorite(favorite: String)
    suspend fun removeFavorite(favorite: String)
    suspend fun clearAllFavorites()
}