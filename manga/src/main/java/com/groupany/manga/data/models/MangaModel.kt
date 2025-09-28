package com.groupany.manga.data.models

data class MangaModel(
    val id: String = "",
    val title: String = "",
    val coverPath: String = "",
    val authors: List<String> = emptyList(),
    val genres: List<String> = emptyList(),
    val status: String = "",
    val chapters: List<String> = emptyList(),
) {
    companion object {
        fun fromMap(map: Map<String, Any?>): MangaModel {
            return MangaModel(
                id = map["id"] as? String ?: "",
                title = map["title"] as? String ?: "",
                coverPath = map["cover_path"] as? String ?: "",
                authors = (map["authors"] as? List<*>)?.filterIsInstance<String>() ?: emptyList(),
                genres = (map["genres"] as? List<*>)?.filterIsInstance<String>() ?: emptyList(),
                status = map["status"] as? String ?: "",
                chapters = (map["chapters"] as? List<*>)?.filterIsInstance<String>() ?: emptyList(),
            )
        }
    }
}