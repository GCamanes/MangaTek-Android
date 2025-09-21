package com.groupany.manga.data.datasources

interface MangaLocalDataSource {
    fun getFavorites(): Set<String>
    fun toggleFavorite(id: String): Set<String>
    fun clearFavorites()
}