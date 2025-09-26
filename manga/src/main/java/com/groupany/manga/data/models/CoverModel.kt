package com.groupany.manga.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "covers")
data class CoverModel(
    @PrimaryKey val mangaId: String,
    val storagePath: String,  // Firebase path
    val cachedUrl: String?,   // Last known download URL
    val lastUpdated: Long     // Timestamp for cache freshness
)