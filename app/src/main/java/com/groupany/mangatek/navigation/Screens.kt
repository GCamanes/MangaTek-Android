package com.groupany.mangatek.navigation

sealed class NavParam(val name: String) {
    object AutoAuth : NavParam("autoAuth")

    val asParam: String
        get() = "{${this.name}}"
}

sealed class Screen(val route: String) {
    object Login : Screen("login/${NavParam.AutoAuth.asParam}")
    object Home : Screen("home")
    object Settings: Screen ("settings")
}