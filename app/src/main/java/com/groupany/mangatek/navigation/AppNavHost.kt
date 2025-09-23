package com.groupany.mangatek.navigation

import android.net.Uri
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
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
import com.groupany.manga.presentation.screens.MangaDetailScreen
import com.groupany.manga.presentation.screens.MangaListScreen
import com.groupany.settings.presentation.screens.SettingsScreen
import com.groupany.ui.constants.UIConstants

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun AppNavHost(navController: NavHostController) {
    SharedTransitionLayout {
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
                LoginScreen(
                    autoAuth,
                    onSuccess = { NavHelper.gotToMangaList(navController) },
                )
            }
            composable(
                Screen.Settings.route,
                enterTransition = {
                    slideInVertically(initialOffsetY = { it }, animationSpec = tween(700))
                },
                exitTransition = {
                    slideOutVertically(targetOffsetY = { it }, animationSpec = tween(700))
                }
            ) {
                SettingsScreen(
                    onBack = { navController.popBackStack() },
                    onLogout = { NavHelper.backToLogin(navController) },
                )
            }
            composable(
                Screen.MangaList.route,
                enterTransition = {
                    when (initialState.destination.route) {
                        Screen.Login.route -> fadeIn(animationSpec = tween(3000))
                        else -> EnterTransition.None // No animation when coming from Settings
                    }
                },
            ) {
                MangaListScreen(
                    actions = {
                        IconButton(onClick = { NavHelper.gotToSettings(navController) }) {
                            Icon(
                                Icons.Outlined.Settings,
                                contentDescription = stringResource(R.string.settings),
                                modifier = Modifier.size(UIConstants.IconHeight),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    },
                    onMangaClick = { id, title, coverUrl ->
                        NavHelper.gotToMangaDetail(navController, id, title, coverUrl)
                    },
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedContentScope = this@composable,
                )
            }
            composable(
                Screen.MangaDetail.route,
                arguments = listOf(
                    navArgument(NavParam.Id.name) { type = NavType.StringType },
                    navArgument(NavParam.Title.name) { type = NavType.StringType },
                    navArgument(NavParam.Url.name) { type = NavType.StringType },
                ),
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getString(NavParam.Id.name)!!
                val title = backStackEntry.arguments?.getString(NavParam.Title.name)!!
                val coverUrl = backStackEntry.arguments?.getString(NavParam.Url.name)!!
                MangaDetailScreen(
                    id = id,
                    title = title,
                    coverUrl = coverUrl,
                    onBack = { navController.popBackStack() },
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedContentScope = this@composable,
                )
            }
        }
    }
}

object NavHelper {
    fun gotToSettings(navController: NavHostController) {
        navController.navigate(Screen.Settings.route)
    }

    fun gotToMangaList(navController: NavHostController) {
        navController.navigate(Screen.MangaList.route) {
            popUpTo(Screen.Login.route) { inclusive = true }
            launchSingleTop = true
        }
    }

    fun gotToMangaDetail(
        navController: NavHostController,
        id: String,
        title: String,
        coverUrl: String,
    ) {
        val encodedTitle = Uri.encode(title)
        val encodedCoverUrl = Uri.encode(coverUrl)
        val route = Screen.MangaDetail.route
            .replace(NavParam.Id.asParam, id)
            .replace(NavParam.Title.asParam, encodedTitle)
            .replace(NavParam.Url.asParam, encodedCoverUrl)

        navController.navigate(route)
    }

    fun backToLogin(navController: NavHostController) {
        val route = Screen.Login.route.replace(NavParam.AutoAuth.asParam, "false")
        navController.navigate(route) {
            popUpTo(0) { inclusive = true } // Clears the entire back stack
        }
    }
}
