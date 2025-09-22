package com.groupany.mangatek.navigation

sealed class NavParam(val name: String) {
    object AutoAuth : NavParam("auto-auth")
    object Id : NavParam("id")
    object Title : NavParam("title")

    val asParam: String
        get() = "{${this.name}}"
}

sealed class Screen(val route: String) {
    object Login : Screen("login/${NavParam.AutoAuth.asParam}")
    object Settings: Screen ("settings")
    object MangaList : Screen("manga-list")
    object MangaDetail : Screen("manga/${NavParam.Id.asParam}/${NavParam.Title.asParam}")
}