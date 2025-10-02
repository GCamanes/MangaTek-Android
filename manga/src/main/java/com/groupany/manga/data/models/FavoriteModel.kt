package com.groupany.manga.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteModel(
    @PrimaryKey val mangaId: String,
)