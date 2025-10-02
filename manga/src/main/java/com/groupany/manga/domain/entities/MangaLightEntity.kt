package com.groupany.manga.domain.entities

data class MangaLightEntity(
    val id: String = "",
    val title: String = "",
    val coverPath: String = "",
    val status: String = "",
    val lastChapter: String = "",
) {
    fun isOnGoing() = status.lowercase() == "ongoing"
}