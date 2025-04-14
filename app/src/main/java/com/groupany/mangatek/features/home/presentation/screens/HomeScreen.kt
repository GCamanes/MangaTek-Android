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
import com.groupany.mangatek.core.presentation.composable.CustomError
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
        contentWindowInsets = WindowInsets(0.dp),
    ) { paddingValues ->
            Box (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = AppDimension.PaddingMedium),
                contentAlignment = Alignment.Center
            ){
                when {
                    uiState.isLoading -> CircularProgressIndicator()
                    uiState.failure != null -> CustomError(
                        uiState.failure!!,
                        onRetry = { viewModel.loadUiState() }
                    )
                    else -> Box (modifier = Modifier.fillMaxSize()){
                        LazyColumn(
                            state = listState,
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(
                                top = AppDimension.PaddingMedium,
                                bottom = AppDimension.PaddingBig,
                            ),
                            verticalArrangement = Arrangement.spacedBy(AppDimension.PaddingMedium)
                        ) {
                            items(uiState.mangaList) {
                                manga -> MangaCard(
                                    manga,
                                    uiState.isFavorite(manga.id),
                                    onToggle = viewModel::toggleFavorite,
                                    getDownloadUrl = viewModel::getDownloadUrl
                                )
                            }
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