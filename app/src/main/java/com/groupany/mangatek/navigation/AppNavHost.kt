package com.groupany.mangatek.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
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
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.groupany.authentication.presentation.screens.LoginScreen
import com.groupany.localization.R
import com.groupany.manga.presentation.screens.MangaDetailScreen
import com.groupany.manga.presentation.screens.MangaListScreen
import com.groupany.manga.presentation.viewmodels.MangaDetailViewModel
import com.groupany.settings.presentation.screens.SettingsScreen
import com.groupany.ui.animation.AnimationUtils.LocalNavAnimatedVisibilityScope
import com.groupany.ui.animation.AnimationUtils.LocalSharedTransitionScope
import com.groupany.ui.constants.UIConstants

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun AppNavHost(navController: NavHostController) {
    SharedTransitionLayout {
        CompositionLocalProvider(
            LocalSharedTransitionScope provides this,
        ) {
            NavHost(
                navController = navController,
                startDestination = NavGraph.Auth.route,
            ) {
                navigation(
                    startDestination = Screen.Login.route,
                    route = NavGraph.Auth.route,
                ) {
                    composable(
                        Screen.Login.route,
                        arguments = listOf(
                            navArgument(NavParam.AutoAuth.name) {
                                type = NavType.BoolType
                                defaultValue = true
                            }
                        ),
                        enterTransition = { fadeIn(animationSpec = tween(1000)) },
                        exitTransition = { fadeOut(animationSpec = tween(1000)) }
                    ) { backStackEntry ->
                        val autoAuth = backStackEntry
                            .arguments?.getBoolean(NavParam.AutoAuth.name) ?: true
                        LoginScreen(
                            autoAuth = autoAuth,
                            onSuccess = { NavHelper.goToMangaList(navController) },
                        )
                    }
                }

                navigation(
                    startDestination = Screen.MangaList.route,
                    route = NavGraph.Main.route,
                ) {
                    composableWithCompositionLocal(
                        Screen.MangaList.route,
                        enterTransition = { fadeIn(animationSpec = tween(3000)) },
                        popEnterTransition = { EnterTransition.None },
                        popExitTransition = { ExitTransition.None },
                    ) {
                        MangaListScreen(
                            actions = {
                                IconButton(onClick = {
                                    NavHelper.goToSettings(navController)
                                }) {
                                    Icon(
                                        Icons.Outlined.Settings,
                                        contentDescription = stringResource(R.string.settings),
                                        modifier = Modifier.size(UIConstants.IconHeight),
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            },
                            onMangaClick = { id, title, path ->
                                NavHelper.goToMangaDetail(navController, id, title, path)
                            },
                        )
                    }

                    composableWithCompositionLocal(
                        Screen.MangaDetail.route,
                        arguments = listOf(
                            navArgument(NavParam.Id.name) { type = NavType.StringType },
                            navArgument(NavParam.Title.name) { type = NavType.StringType },
                            navArgument(NavParam.Path.name) { type = NavType.StringType },
                        ),
                    ) { backStackEntry ->
                        val id = backStackEntry.arguments?.getString(NavParam.Id.name)!!
                        val title = backStackEntry.arguments?.getString(NavParam.Title.name)!!

                        // Pass the backStackEntry so Hilt can use its SavedStateHandle
                        val viewModel: MangaDetailViewModel = hiltViewModel(backStackEntry)

                        MangaDetailScreen(
                            id = id,
                            title = title,
                            onBack = {
                                navController.popBackStack(
                                    route = Screen.MangaList.route,
                                    inclusive = false
                                )
                            },
                            viewModel = viewModel,
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
                            onBack = {
                                navController.popBackStack(
                                    route = Screen.MangaList.route,
                                    inclusive = false
                                )
                            },
                            onLogout = { NavHelper.backToLogin(navController) },
                        )
                    }
                }
            }
        }
    }
}

object NavHelper {
    fun goToMangaList(navController: NavHostController) {
        val currentRoute = navController.currentBackStackEntry?.destination?.route
        val args = navController.currentBackStackEntry?.arguments
        val autoAuth = args?.getBoolean(NavParam.AutoAuth.name)
        navController.navigate(Screen.MangaList.route) {
            popUpTo(Screen.Login.route) { inclusive = true }
            launchSingleTop = true
        }
    }

    fun goToMangaDetail(
        navController: NavHostController,
        id: String,
        title: String,
        path: String,
    ) {
        navController.navigate(Screen.MangaDetail.route(id, title, path)) {
            launchSingleTop = true
        }
    }

    fun goToSettings(navController: NavHostController) {
        navController.navigate(Screen.Settings.route) {
            launchSingleTop = true
        }
    }

    fun backToLogin(navController: NavHostController) {
        navController.navigate(Screen.Login.route(autoAuth = false)) {
            popUpTo(NavGraph.Main.route) { inclusive = true }
            launchSingleTop = true
        }
    }
}

fun NavGraphBuilder.composableWithCompositionLocal(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    enterTransition: (
    @JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?
    )? = {
        fadeIn()
    },
    exitTransition: (
    @JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?
    )? = {
        fadeOut()
    },
    popEnterTransition: (
    @JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?
    )? =
        enterTransition,
    popExitTransition: (
    @JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?
    )? =
        exitTransition,
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
) {
    composable(
        route,
        arguments,
        deepLinks,
        enterTransition,
        exitTransition,
        popEnterTransition,
        popExitTransition,
    ) {
        CompositionLocalProvider(
            LocalNavAnimatedVisibilityScope provides this@composable,
        ) {
            content(it)
        }
    }
}