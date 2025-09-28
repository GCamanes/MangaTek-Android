package com.groupany.manga.data.models

data class MangaLightModel(
    val id: String = "",
    val title: String = "",
    val coverPath: String = "",
    val status: String = "",
    val lastChapter: String = ""
) {
    companion object {
        fun fromMap(map: Map<String, Any?>): MangaLightModel {
            return MangaLightModel(
                id = map["id"] as? String ?: "",
                title = map["title"] as? String ?: "",
                coverPath = map["cover_path"] as? String ?: "",
                status = map["status"] as? String ?: "",
                lastChapter = when (val chapter = map["last_chapter"]) {
                    is String -> chapter
                    is Number -> chapter.toString()
                    else -> ""
                }
            )
        }
    }
}