package com.groupany.manga.data.datasources

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.groupany.manga.data.models.CoverModel

@Dao
interface CoverDao {
    @Query("SELECT * FROM covers WHERE mangaId = :id")
    suspend fun getCover(id: String): CoverModel?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCover(manga: CoverModel)
}