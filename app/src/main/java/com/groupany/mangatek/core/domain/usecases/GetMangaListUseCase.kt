package com.groupany.mangatek.core.domain.usecases

import com.groupany.mangatek.core.domain.CustomFailure
import com.groupany.mangatek.core.domain.CustomResult
import com.groupany.mangatek.core.domain.entities.MangaLightEntity
import com.groupany.mangatek.core.domain.repositories.MangaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class GetMangaListUseCase @Inject constructor(private val repository: MangaRepository) {
    operator fun invoke(): Flow<CustomResult<List<MangaLightEntity>>> {
        return repository.getMangaList()
            .map { list -> CustomResult.Success(list) as CustomResult<List<MangaLightEntity>> }
            .catch { e -> emit(CustomResult.Failure(handleException(e)).toFailure()) }
    }
}

fun handleException(e: Throwable): CustomFailure {
    return when (e) {
        is IOException -> CustomFailure.NetworkError()
        else -> CustomFailure.Unknown(e.message)
    }
}