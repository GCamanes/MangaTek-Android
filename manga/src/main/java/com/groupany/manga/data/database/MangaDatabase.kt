package com.groupany.manga.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.groupany.manga.data.datasources.CoverDao
import com.groupany.manga.data.datasources.FavoriteDao
import com.groupany.manga.data.models.CoverModel
import com.groupany.manga.data.models.FavoriteModel

@Database(entities = [CoverModel::class, FavoriteModel::class], version = 2)
abstract class MangaDatabase : RoomDatabase() {
    abstract fun coverDao(): CoverDao
    abstract fun favoriteDao(): FavoriteDao
}