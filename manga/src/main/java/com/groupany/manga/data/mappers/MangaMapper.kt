package com.groupany.manga.data.mappers

import com.groupany.manga.data.models.MangaLightModel
import com.groupany.manga.domain.entities.MangaLightEntity

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