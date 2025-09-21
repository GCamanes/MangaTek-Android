package com.groupany.mangatek.core.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.groupany.authentication.presentation.screens.LoginScreen
import com.groupany.localization.R
import com.groupany.manga.presentation.screens.HomeScreen
import com.groupany.mangatek.features.settings.presentation.screens.SettingsScreen
import com.groupany.ui.constants.UIConstants

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
            LoginScreen(autoAuth, onSuccess = { NavHelper.gotToHome(navController) })
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
            HomeScreen(actions = {
                IconButton(onClick = { NavHelper.gotToSettings(navController) }) {
                    Icon(
                        Icons.Outlined.Settings,
                        contentDescription = stringResource(R.string.settings),
                        modifier = Modifier.size(UIConstants.IconHeight),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            })
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