package com.groupany.mangatek.core.domain.entities

data class MangaLightEntity(
    val id: String = "",
    val title: String = "",
    val coverPath: String = "",
    val authors: List<String> = emptyList(),
    val genres: List<String> = emptyList(),
    val status: String = "",
    val lastChapter: String = ""
) {
    fun isOnGoing() = status.lowercase() == "ongoing"
}