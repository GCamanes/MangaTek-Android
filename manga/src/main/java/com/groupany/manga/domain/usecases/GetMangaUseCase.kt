package com.groupany.manga.domain.usecases

import com.groupany.base.CustomResult
import com.groupany.base.usecases.FlowUseCase
import com.groupany.manga.domain.entities.MangaEntity
import com.groupany.manga.domain.repositories.MangaRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMangaUseCase @Inject constructor(private val repository: MangaRepository) :
    FlowUseCase<String, CustomResult<MangaEntity>>() {
    override suspend fun invoke(input: String): Flow<CustomResult<MangaEntity>> {
        return safeCallFlow {
            repository.getManga(input)
        }
    }
}