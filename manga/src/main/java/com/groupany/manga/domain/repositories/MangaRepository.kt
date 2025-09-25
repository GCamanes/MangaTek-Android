package com.groupany.manga.domain.repositories

import com.groupany.manga.domain.entities.MangaLightEntity
import kotlinx.coroutines.flow.Flow

interface MangaRepository {
    fun getMangaList(): Flow<List<MangaLightEntity>>
    fun getAllFavorites(): Flow<Set<String>>
    fun isFavorite(id: String): Flow<Boolean>
    suspend fun toggleAFavorite(favorite: String)
    suspend fun clearAllFavorites()
}