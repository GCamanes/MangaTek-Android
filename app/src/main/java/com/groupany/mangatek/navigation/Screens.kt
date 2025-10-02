package com.groupany.mangatek.navigation

import android.net.Uri

sealed class NavParam(val name: String) {
    object AutoAuth : NavParam("auto-auth")
    object Id : NavParam("id")
    object Title : NavParam("title")
    object Path : NavParam("path")

    val asParam: String
        get() = "{${this.name}}"
}

sealed class Screen(val route: String) {
    object Login : Screen("login/${NavParam.AutoAuth.asParam}") {
        fun route(autoAuth: Boolean = true) = "login/$autoAuth"
    }
    object MangaList : Screen("manga-list")
    object MangaDetail :
        Screen("manga/${NavParam.Id.asParam}/${NavParam.Title.asParam}/${NavParam.Path.asParam}") {
        fun route(id: String, title: String, path: String): String {
            val encodedTitle = Uri.encode(title)
            val encodedPath = Uri.encode(path)
            return "manga/$id/$encodedTitle/$encodedPath"
        }
    }

    object Settings : Screen("settings")
}