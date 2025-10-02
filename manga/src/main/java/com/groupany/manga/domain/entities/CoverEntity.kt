package com.groupany.manga.domain.entities


data class CoverEntity(
    val mangaId: String,
    val storagePath: String,
    val cachedUrl: String?,
    val lastUpdated: Long
)