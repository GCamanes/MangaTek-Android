package com.groupany.mangatek.features.home.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.groupany.mangatek.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.groupany.mangatek.core.constants.AppDimension
import com.groupany.mangatek.core.helpers.NavHelper
import com.groupany.mangatek.core.presentation.screens.CustomError
import com.groupany.mangatek.core.presentation.composable.MangaTekTitle
import com.groupany.mangatek.features.home.presentation.composables.MangaCard
import com.groupany.mangatek.features.home.presentation.viewmodels.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController, viewModel: HomeViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val listState = rememberLazyListState()

    // Convert padding value to pixels (Int)
    val maxOffsetPx = with(LocalDensity.current) { AppDimension.PaddingMedium.roundToPx() }

    var isFabExpanded by remember { mutableStateOf(false) }
    var filteredOnFavorites by remember { mutableStateOf(false) }

    val alpha by remember {
        derivedStateOf {
            if (listState.firstVisibleItemIndex > 0) {
                1f // Show gradient once scrolled past first item
            } else {
                val offset = listState.firstVisibleItemScrollOffset.coerceAtMost(maxOffsetPx)
                0f + (offset.toFloat() / maxOffsetPx.toFloat())
            }
        }
    }

    Scaffold(
        contentWindowInsets = WindowInsets(0.dp),
        topBar = {
            TopAppBar(
                title = { MangaTekTitle() },
                actions = {
                    IconButton(onClick = { NavHelper.gotToSettings(navController) }) {
                        Icon(
                            Icons.Outlined.Settings,
                            contentDescription = stringResource(R.string.settings),
                            modifier = Modifier.size(AppDimension.IconHeight),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
            )
        },
        floatingActionButton = {
            Column(
                modifier = Modifier.padding(bottom = AppDimension.PaddingSmall),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(AppDimension.PaddingSmall)
            ) {
                if (isFabExpanded) {
                    SmallFloatingActionButton(
                        onClick = {
                            // Handle "Favorites"
                            isFabExpanded = false
                            filteredOnFavorites = true
                        },
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = if (filteredOnFavorites) MaterialTheme.colorScheme.secondary
                        else MaterialTheme.colorScheme.primary,
                    ) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Favorites",
                            Modifier.size(AppDimension.IconHeight)
                        )
                    }

                    SmallFloatingActionButton(
                        onClick = {
                            // Handle "All"
                            isFabExpanded = false
                            filteredOnFavorites = false
                        },
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = if (filteredOnFavorites) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.secondary,
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "All",
                            Modifier.size(AppDimension.IconHeight)
                        )
                    }
                }

                FloatingActionButton(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.primary,
                    onClick = { isFabExpanded = !isFabExpanded }
                ) {
                    Icon(
                        imageVector = if (isFabExpanded) Icons.Default.Close else Icons.Default.FilterList,
                        contentDescription = "Toggle Filter",
                        Modifier.size(AppDimension.IconHeight)
                    )
                }
            }
        },
    ) { paddingValues ->
            Box (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ){
                when {
                    uiState.isLoading -> CircularProgressIndicator()
                    uiState.failure != null -> CustomError(
                            uiState.failure!!,
                            onRetry = { viewModel.loadUiState() }
                        )
                    else -> LazyColumn(
                            state = listState,
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(
                                top = AppDimension.PaddingMedium,
                                start = AppDimension.PaddingMedium,
                                end = AppDimension.PaddingMedium,
                                bottom = AppDimension.PaddingBig + AppDimension.PaddingLarge,
                            ),
                            verticalArrangement = Arrangement.spacedBy(AppDimension.PaddingMedium)
                        ) {
                            items(uiState.mangaList) { manga -> MangaCard(
                                    manga,
                                    uiState.isFavorite(manga.id),
                                    onToggle = viewModel::toggleFavorite,
                                    getCachedUrl = viewModel::getCachedUrl,
                                    getDownloadUrl = viewModel::getDownloadUrl
                                )
                            }
                        }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(AppDimension.PaddingMedium)
                        .alpha(alpha)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.surface,
                                    Color.Transparent,
                                )
                            )
                        )
                        .align(Alignment.TopCenter)
                )
            }
    }
}