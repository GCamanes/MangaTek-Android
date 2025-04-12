package com.groupany.mangatek.core.data.datasources

import com.groupany.mangatek.core.data.models.MangaLightModel
import kotlinx.coroutines.flow.Flow

interface MangaRemoteDataSource {
    fun getMangaList(): Flow<List<MangaLightModel>>
}