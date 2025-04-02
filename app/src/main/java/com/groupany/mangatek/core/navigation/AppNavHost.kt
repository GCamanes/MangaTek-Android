package com.groupany.mangatek.core.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.groupany.mangatek.features.home.presentation.screens.HomeScreen
import com.groupany.mangatek.features.auth.presentation.screens.LoginScreen
import com.groupany.mangatek.features.settings.presentation.screens.SettingsScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Login.route) {
        composable(
            Screen.Login.route,
            arguments = listOf(
                navArgument(NavParam.AutoAuth.name) {
                    type = NavType.BoolType
                    defaultValue = true
                    nullable = false
                }
            ),
            enterTransition = { fadeIn(animationSpec = tween(1000)) },
            exitTransition = { fadeOut(animationSpec = tween(1000)) }
        ) { backStackEntry ->
            val autoAuth = backStackEntry.arguments?.getBoolean(NavParam.AutoAuth.name) != false
            LoginScreen(navController, autoAuth)
        }
        composable(
            Screen.Home.route,
            enterTransition = {
                when (initialState.destination.route) {
                    Screen.Login.route -> fadeIn(animationSpec = tween(3000))
                    else -> EnterTransition.None // No animation when coming from Settings
                }
            },
        ) {
            HomeScreen(navController)
        }
        composable(Screen.Settings.route,
            enterTransition = {
                slideInVertically(initialOffsetY = { it }, animationSpec = tween(700))
            },
            exitTransition = {
                slideOutVertically(targetOffsetY = { it }, animationSpec = tween(700))
            }
        ) {
            SettingsScreen(navController)
        }
    }
}