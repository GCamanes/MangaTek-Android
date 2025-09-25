package com.groupany.manga.data.datasources

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.groupany.manga.data.models.FavoriteModel
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorites")
    fun getAllFavorites(): Flow<List<FavoriteModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(favorite: FavoriteModel)

    @Delete
    suspend fun removeFavorite(favorite: FavoriteModel)

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE mangaId = :id)")
    fun isFavorite(id: String): Flow<Boolean>
}