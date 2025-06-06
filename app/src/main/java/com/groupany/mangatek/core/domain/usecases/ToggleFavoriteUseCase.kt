package com.groupany.mangatek.core.domain.usecases

import com.groupany.base.usecases.UseCase
import com.groupany.mangatek.core.domain.repositories.MangaRepository
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(private val repo: MangaRepository): UseCase<String, Set<String>>() {
    override suspend fun invoke(id: String): Set<String> {
        return repo.toggleFavorite(id)
    }
}