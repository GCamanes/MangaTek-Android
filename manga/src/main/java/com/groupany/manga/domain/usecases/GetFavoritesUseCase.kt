package com.groupany.manga.domain.usecases

import com.groupany.base.usecases.NoParamUseCase
import com.groupany.manga.domain.repositories.MangaRepository
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(private val repo: MangaRepository): NoParamUseCase<Set<String>>() {
    override suspend fun invoke(): Set<String> {
        return repo.getFavorites()
    }
}