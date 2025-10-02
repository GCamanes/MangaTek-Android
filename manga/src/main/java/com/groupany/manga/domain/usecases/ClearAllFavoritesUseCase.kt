package com.groupany.manga.domain.usecases

import com.groupany.base.usecases.BlankUseCase
import com.groupany.manga.domain.repositories.MangaRepository
import javax.inject.Inject

class ClearAllFavoritesUseCase @Inject constructor(private val repo: MangaRepository) :
    BlankUseCase() {
    override suspend fun invoke() {
        repo.clearAllFavorites()
    }
}