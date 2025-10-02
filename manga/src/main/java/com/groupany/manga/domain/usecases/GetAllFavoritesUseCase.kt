package com.groupany.manga.domain.usecases

import com.groupany.base.CustomResult
import com.groupany.base.usecases.NoParamFlowUseCase
import com.groupany.manga.domain.repositories.MangaRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetAllFavoritesUseCase @Inject constructor(private val repository: MangaRepository) :
    NoParamFlowUseCase<CustomResult<Set<String>>>() {
    override suspend fun invoke(): Flow<CustomResult<Set<String>>> {
        return safeCallFlow {
            repository.getAllFavorites()
        }
    }
}