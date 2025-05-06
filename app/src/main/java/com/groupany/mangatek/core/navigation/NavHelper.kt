package com.groupany.mangatek.core.navigation

import androidx.navigation.NavHostController

object NavHelper {
    fun gotToHome(navController: NavHostController) {
        navController.navigate(Screen.Home.route) {
            popUpTo(Screen.Login.route) { inclusive = true }
            launchSingleTop = true
        }
    }

    fun gotToSettings(navController: NavHostController) {
        navController.navigate(Screen.Settings.route)
    }

    fun backToLogin(navController: NavHostController) {
        val route = Screen.Login.route.replace(NavParam.AutoAuth.asParam, "false")
        navController.navigate(route) {
            popUpTo(0) { inclusive = true } // Clears the entire back stack
        }
    }
}