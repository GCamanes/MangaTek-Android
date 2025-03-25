package com.groupany.mangatek.core.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Home : Screen("home")
    object Settings: Screen ("settings")
}