package com.groupany.mangatek.core.navigation

sealed class ScreenParam(val name: String) {
    object AutoAuth : ScreenParam("autoAuth")

    val asParam: String
        get() = "{$this}"
}

sealed class Screen(val route: String) {
    object Login : Screen("login/{autoAuth}")
    object Home : Screen("home")
    object Settings: Screen ("settings")
}