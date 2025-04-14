package com.groupany.mangatek.core.data.datasources

interface MangaLocalDataSource {
    fun getFavorites(): Set<String>
    fun toggleFavorite(id: String): Set<String>
    fun clearFavorites()
}