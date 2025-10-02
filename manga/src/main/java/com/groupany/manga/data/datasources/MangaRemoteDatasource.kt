package com.groupany.manga.data.datasources

import com.groupany.manga.data.models.MangaLightModel
import com.groupany.manga.data.models.MangaModel
import kotlinx.coroutines.flow.Flow

interface MangaRemoteDataSource {
    fun getMangaList(): Flow<List<MangaLightModel>>
    fun getManga(id: String): Flow<MangaModel>
}