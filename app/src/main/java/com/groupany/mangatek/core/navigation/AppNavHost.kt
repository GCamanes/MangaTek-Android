package com.groupany.mangatek.core.navigation

import android.app.Activity
import android.view.WindowInsetsController
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.groupany.mangatek.features.home.presentation.screens.HomeScreen
import com.groupany.mangatek.features.login.presentation.screens.LoginScreen
import com.groupany.mangatek.features.settings.presentation.screens.SettingsScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    SetStatusBarColor()
    NavHost(navController = navController, startDestination = Screen.Login.route) {
        composable(Screen.Login.route) {
            LoginScreen(navController)
        }
        composable(Screen.Home.route) {
            HomeScreen(navController)
        }
        composable(Screen.Settings.route) {
            SettingsScreen(navController)
        }
    }
}

@Composable
fun SetStatusBarColor() {
    val activity = LocalActivity.current as Activity
    val window = activity.window

    // Make sure system bars are drawn behind content
    WindowCompat.setDecorFitsSystemWindows(window, false)

    window.insetsController?.setSystemBarsAppearance(
        0, // No flags = light icons (white)
        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
    )
}