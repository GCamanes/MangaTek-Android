package com.groupany.manga.data.repositories

import com.groupany.manga.data.datasources.FavoriteDao
import com.groupany.manga.data.datasources.MangaLocalDataSource
import com.groupany.manga.data.datasources.MangaRemoteDataSource
import com.groupany.manga.data.mappers.MangaMapper
import com.groupany.manga.data.models.FavoriteModel
import com.groupany.manga.domain.entities.MangaLightEntity
import com.groupany.manga.domain.repositories.MangaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MangaRepositoryImpl(
    private val remoteDataSource: MangaRemoteDataSource,
    private val localDataSource: MangaLocalDataSource,
    private val dao: FavoriteDao,
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
    override fun getAllFavorites(): Flow<List<String>> {
        return dao.getAllFavorites().map { list -> list.map { it.mangaId } }
    }

    override fun isFavorite(id: String): Flow<Boolean> {
        return dao.isFavorite(id)
    }

    override suspend fun addFavorite(favorite: String) {
        dao.addFavorite(FavoriteModel(mangaId = favorite))
    }

    override suspend fun removeFavorite(favorite: String) {
        dao.removeFavorite(FavoriteModel(mangaId = favorite))
    }

    override suspend fun clearAllFavorites() {
        dao.clearAllFavorites()
    }
}