package com.groupany.manga.data.mappers

import com.groupany.manga.data.models.CoverModel
import com.groupany.manga.domain.entities.CoverEntity

object CoverMapper {
    fun toEntity(model: CoverModel): CoverEntity = CoverEntity(
        mangaId = model.mangaId,
        storagePath = model.storagePath,
        cachedUrl = model.cachedUrl,
        lastUpdated = model.lastUpdated,
    )

    fun toModel(entity: CoverEntity): CoverModel = CoverModel(
        mangaId = entity.mangaId,
        storagePath = entity.storagePath,
        cachedUrl = entity.cachedUrl,
        lastUpdated = entity.lastUpdated,
    )
}