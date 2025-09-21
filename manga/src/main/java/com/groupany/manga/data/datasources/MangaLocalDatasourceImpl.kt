package com.groupany.manga.data.datasources

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

const val FAVORITES_KEY = "favorites_prefs"

class MangaLocalDataSourceImpl(private val context: Context) : MangaLocalDataSource {
    private fun saveFavorites(favoriteIds: Set<String>) : Set<String> {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(FAVORITES_KEY, Context.MODE_PRIVATE)
        sharedPreferences.edit {
            putStringSet(FAVORITES_KEY, favoriteIds)
        }
        return favoriteIds
    }

    override fun getFavorites(): Set<String> {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(FAVORITES_KEY, Context.MODE_PRIVATE)

        return sharedPreferences.getStringSet(FAVORITES_KEY, emptySet()) ?: emptySet()
    }

    override fun toggleFavorite(id: String): Set<String> {
        val currentFavorites = getFavorites().toMutableSet()
        if (currentFavorites.contains(id)) {
            currentFavorites.remove(id) // Remove from favorites
        } else {
            currentFavorites.add(id) // Add to favorites
        }
        saveFavorites(currentFavorites)
        return currentFavorites
    }

    override fun clearFavorites() { saveFavorites(emptySet()) }
}