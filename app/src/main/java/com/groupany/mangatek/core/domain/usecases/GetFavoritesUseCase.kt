package com.groupany.mangatek.core.domain.usecases

import com.groupany.mangatek.core.domain.repositories.MangaRepository
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(private val repo: MangaRepository): NoParamUseCase<Set<String>>() {
    override suspend fun invoke(): Set<String> {
        return repo.getFavorites()
    }
}