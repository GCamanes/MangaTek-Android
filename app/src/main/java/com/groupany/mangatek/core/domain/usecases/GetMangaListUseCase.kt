package com.groupany.mangatek.core.domain.usecases

import com.groupany.base.CustomResult
import com.groupany.base.usecases.NoParamFlowUseCase
import com.groupany.mangatek.core.domain.entities.MangaLightEntity
import com.groupany.mangatek.core.domain.repositories.MangaRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMangaListUseCase @Inject constructor(private val repository: MangaRepository) :
    NoParamFlowUseCase<CustomResult<List<MangaLightEntity>>>()
{
    override suspend fun invoke(): Flow<CustomResult<List<MangaLightEntity>>> {
        return safeCallFlow {
            repository.getMangaList()
        }
    }
}