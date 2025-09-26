package com.groupany.manga.domain.usecases

import com.groupany.base.CustomResult
import com.groupany.base.usecases.UseCase
import com.groupany.manga.domain.entities.CoverEntity
import com.groupany.manga.domain.repositories.MangaRepository
import javax.inject.Inject

class GetMangaCoverUseCase @Inject constructor(private val repo: MangaRepository) :
    UseCase<GetMangaCoverParams, CustomResult<CoverEntity?>>() {
    override suspend fun invoke(input: GetMangaCoverParams): CustomResult<CoverEntity?> {
        return safeCall {
            repo.getMangaCover(input.id, input.coverPath)
        }
    }
}

class GetMangaCoverParams(val id: String, val coverPath: String)
