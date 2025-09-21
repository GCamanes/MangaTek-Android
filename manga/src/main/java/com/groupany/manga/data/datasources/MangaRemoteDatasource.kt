package com.groupany.manga.data.datasources

import com.groupany.manga.data.models.MangaLightModel
import kotlinx.coroutines.flow.Flow

interface MangaRemoteDataSource {
    fun getMangaList(): Flow<List<MangaLightModel>>
}