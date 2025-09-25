package com.groupany.manga.domain.usecases

import com.groupany.base.CustomResult
import com.groupany.base.usecases.FlowUseCase
import com.groupany.manga.domain.repositories.MangaRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IsFavoriteUseCase @Inject constructor(private val repository: MangaRepository) :
    FlowUseCase<String, CustomResult<Boolean>>() {
    override suspend fun invoke(input: String): Flow<CustomResult<Boolean>> {
        return safeCallFlow {
            repository.isFavorite(input)
        }
    }
}