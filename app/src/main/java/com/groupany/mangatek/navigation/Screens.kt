package com.groupany.mangatek.navigation

sealed class NavParam(val name: String) {
    object AutoAuth : NavParam("autoAuth")

    val asParam: String
        get() = "{${this.name}}"
}

sealed class Screen(val route: String) {
    object Login : Screen("login/${NavParam.AutoAuth.asParam}")
    object MangaList : Screen("manga-list")
    object Settings: Screen ("settings")
}