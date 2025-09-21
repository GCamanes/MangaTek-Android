package com.groupany.manga.domain.repositories

import com.groupany.manga.domain.entities.MangaLightEntity
import kotlinx.coroutines.flow.Flow

interface MangaRepository {
    fun getMangaList(): Flow<List<MangaLightEntity>>
    fun getFavorites(): Set<String>
    fun toggleFavorite(id: String): Set<String>
    fun clearFavorites()
}