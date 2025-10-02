package com.groupany.mangatek.navigation

sealed class NavGraph(val route: String) {
    object Auth : NavGraph("auth_graph")
    object Main : NavGraph("main_graph")
}