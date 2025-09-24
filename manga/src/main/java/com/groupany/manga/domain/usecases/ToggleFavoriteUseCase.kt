package com.groupany.manga.domain.usecases

import com.groupany.base.usecases.UseCase
import com.groupany.manga.domain.repositories.MangaRepository
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(private val repo: MangaRepository): UseCase<String, Set<String>>() {
    override suspend fun invoke(input: String): Set<String> {
        return repo.toggleFavorite(input)
    }
}