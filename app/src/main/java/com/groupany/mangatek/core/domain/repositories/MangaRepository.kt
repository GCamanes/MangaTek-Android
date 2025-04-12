package com.groupany.mangatek.core.domain.repositories

import com.groupany.mangatek.core.domain.entities.MangaLightEntity
import kotlinx.coroutines.flow.Flow

interface MangaRepository {
    fun getMangaList(): Flow<List<MangaLightEntity>>
}