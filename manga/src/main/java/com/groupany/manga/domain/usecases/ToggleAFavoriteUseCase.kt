package com.groupany.manga.domain.usecases

import com.groupany.base.usecases.UseCase
import com.groupany.manga.domain.repositories.MangaRepository
import javax.inject.Inject

class ToggleAFavoriteUseCase @Inject constructor(private val repo: MangaRepository) :
    UseCase<String, Unit>() {
    override suspend fun invoke(input: String): Unit {
        return repo.toggleAFavorite(input)
    }
}