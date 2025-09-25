package com.groupany.manga.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.groupany.manga.data.datasources.FavoriteDao
import com.groupany.manga.data.models.FavoriteModel

@Database(entities = [FavoriteModel::class], version = 1)
abstract class MangaDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao

    /*companion object {
        // Static schema location
        const val SCHEMA_EXPORT_DIRECTORY = "../schemas"
    }*/
}