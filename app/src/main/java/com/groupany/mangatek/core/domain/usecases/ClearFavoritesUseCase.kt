package com.groupany.mangatek.core.domain.usecases

import com.groupany.base.usecases.BlankUseCase
import com.groupany.mangatek.core.domain.repositories.MangaRepository
import javax.inject.Inject

class ClearFavoritesUseCase @Inject constructor(private val repo: MangaRepository): BlankUseCase() {
    override suspend fun invoke() { repo.clearFavorites() }
}