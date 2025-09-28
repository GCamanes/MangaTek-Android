package com.groupany.manga.domain.entities

data class MangaEntity(
    val id: String = "",
    val title: String = "",
    val coverPath: String = "",
    val authors: List<String> = emptyList(),
    val genres: List<String> = emptyList(),
    val status: String = "",
    val chapters: List<String> = emptyList(),
) {
    fun isOnGoing() = status.lowercase() == "ongoing"

    // fun getFilteredGenres() = genres.filter { it -> it.lowercase() != "manga" }


    fun getFilteredAuthors() = authors.filter { str ->
        str.all { it.code in 0x00..0x7F } and !filteredAuthors.contains(str) // Only allow ASCII characters
    }.take(3)

    private val filteredAuthors = listOf(
        "Young Magazine",
        "Shounen Magazine",
        "Bessatsu Shounen Magazine",
        "Shounen Jump",
        "Jump SQ.",
        "Shounen Gangan",
        "Young Jump",
        "Ultra Jump",
        "Young Gangan",
        "Gundam Ace",
        "MangaONE",
        "so-bin",
        "soubin",
        "Morning",
    )
}