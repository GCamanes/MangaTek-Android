package com.groupany.mangatek.core.data.mappers

import com.groupany.mangatek.core.data.models.MangaLightModel
import com.groupany.mangatek.core.domain.entities.MangaLightEntity

object MangaMapper {
    fun toModelLightEntity(model: MangaLightModel): MangaLightEntity = MangaLightEntity(
        id = model.id,
        title = model.title,
        coverPath = model.coverPath,
        authors = model.authors,
        genres = model.genres,
        status = model.status,
        lastChapter = model.lastChapter
    )
}