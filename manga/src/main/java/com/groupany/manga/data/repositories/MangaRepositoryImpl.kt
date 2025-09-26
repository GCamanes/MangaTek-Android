package com.groupany.manga.data.repositories

import com.groupany.base.repositories.BaseRepository
import com.groupany.firebase.data.datasources.RemoteStorageDataSource
import com.groupany.manga.data.datasources.CoverDao
import com.groupany.manga.data.datasources.FavoriteDao
import com.groupany.manga.data.datasources.MangaRemoteDataSource
import com.groupany.manga.data.mappers.CoverMapper
import com.groupany.manga.data.mappers.MangaMapper
import com.groupany.manga.data.models.FavoriteModel
import com.groupany.manga.domain.entities.CoverEntity
import com.groupany.manga.domain.entities.MangaLightEntity
import com.groupany.manga.domain.repositories.MangaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class MangaRepositoryImpl(
    private val remoteDataSource: MangaRemoteDataSource,
    private val firebaseDataSource: RemoteStorageDataSource,
    private val coverDao: CoverDao,
    private val favoriteDao: FavoriteDao,
) : MangaRepository, BaseRepository() {

    override fun getMangaList(): Flow<List<MangaLightEntity>> {
        return remoteDataSource.getMangaList()
            .map { modelList -> modelList.map {
                    model -> MangaMapper.toModelLightEntity(model)
                }
            }
    }

    override suspend fun getMangaCover(id: String, coverPath: String): CoverEntity? {
        val cover = coverDao.getCover(id = id)

        // If cached URL exists and is recent, return it
        if (cover != null && cover.cachedUrl != null && !isExpired(cover.lastUpdated)) {
            return CoverMapper.toEntity(cover)
        }

        return safeCall {
            val coverUrl = firebaseDataSource.getDownloadUrl(coverPath)
            val coverUpdated = CoverEntity(
                mangaId = id,
                storagePath = coverPath,
                cachedUrl = coverUrl,
                lastUpdated = System.currentTimeMillis(),
            )
            coverDao.insertCover(CoverMapper.toModel(coverUpdated))
            coverUpdated
        }
    }

    private fun isExpired(lastUpdated: Long): Boolean {
        val halfDay = 12 * 60 * 60 * 1000L
        return System.currentTimeMillis() - lastUpdated > halfDay
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