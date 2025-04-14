package com.groupany.mangatek.core.data.repositories

import com.groupany.mangatek.core.data.datasources.MangaLocalDataSource
import com.groupany.mangatek.core.data.datasources.MangaRemoteDataSource
import com.groupany.mangatek.core.data.mappers.MangaMapper
import com.groupany.mangatek.core.domain.entities.MangaLightEntity
import com.groupany.mangatek.core.domain.repositories.MangaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MangaRepositoryImpl(
    private val remoteDataSource: MangaRemoteDataSource,
    private val localDataSource: MangaLocalDataSource,
) : MangaRepository {

    override fun getMangaList(): Flow<List<MangaLightEntity>> {
        return remoteDataSource.getMangaList()
            .map { modelList -> modelList.map {
                    model -> MangaMapper.toModelLightEntity(model)
                }
            }
    }

    override fun getFavorites(): Set<String> = localDataSource.getFavorites()

    override fun toggleFavorite(id: String): Set<String> = localDataSource.toggleFavorite(id)
}