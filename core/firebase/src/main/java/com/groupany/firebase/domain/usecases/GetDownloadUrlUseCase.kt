package com.groupany.firebase.domain.usecases

import com.groupany.base.CustomResult
import com.groupany.base.usecases.UseCase
import com.groupany.firebase.domain.repositories.RemoteStorageRepository
import javax.inject.Inject

class GetDownloadedUrlUseCase @Inject constructor(private val repo: RemoteStorageRepository): UseCase<String, CustomResult<String>>() {
    override suspend fun invoke(input: String): CustomResult<String> {
        return safeCall<String> {
            repo.getDownloadUrl(input)
        }
    }
}