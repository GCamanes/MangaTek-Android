package com.groupany.manga.data.repositories

import com.groupany.manga.data.datasources.CoverDao
import com.groupany.manga.data.datasources.FavoriteDao
import com.groupany.manga.data.datasources.MangaRemoteDataSource
import com.groupany.manga.data.mappers.MangaMapper
import com.groupany.manga.data.models.FavoriteModel
import com.groupany.manga.domain.entities.MangaLightEntity
import com.groupany.manga.domain.repositories.MangaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class MangaRepositoryImpl(
    private val remoteDataSource: MangaRemoteDataSource,
    private val coverDao: CoverDao,
    private val favoriteDao: FavoriteDao,
) : MangaRepository {

    override fun getMangaList(): Flow<List<MangaLightEntity>> {
        return remoteDataSource.getMangaList()
            .map { modelList -> modelList.map {
                    model -> MangaMapper.toModelLightEntity(model)
                }
            }
    }

    override fun getAllFavorites(): Flow<Set<String>> {
        return favoriteDao.getAllFavorites().map { list ->
            list.map { it.mangaId }.toSet()
        }
    }

    override fun isFavorite(id: String): Flow<Boolean> {
        return favoriteDao.isFavorite(id)
    }

    override suspend fun toggleAFavorite(favorite: String) {
        val currentlyFavorite = favoriteDao.isFavorite(favorite).first()
        if (currentlyFavorite) {
            favoriteDao.removeFavorite(FavoriteModel(mangaId = favorite))
        } else {
            favoriteDao.addFavorite(FavoriteModel(mangaId = favorite))
        }
    }

    override suspend fun clearAllFavorites() {
        favoriteDao.clearAllFavorites()
    }
}