package com.groupany.manga.data.repositories

import com.groupany.manga.data.datasources.MangaLocalDataSource
import com.groupany.manga.data.datasources.MangaRemoteDataSource
import com.groupany.manga.data.mappers.MangaMapper
import com.groupany.manga.domain.entities.MangaLightEntity
import com.groupany.manga.domain.repositories.MangaRepository
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

    override fun clearFavorites() { localDataSource.clearFavorites() }
}