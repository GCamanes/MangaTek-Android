package com.groupany.mangatek.core.helpers

import androidx.navigation.NavHostController
import com.groupany.mangatek.core.navigation.Screen

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
        navController.navigate(Screen.Login.route.replace("{autoAuth}", "false")) {
            popUpTo(0) { inclusive = true } // Clears the entire back stack
        }
    }
}