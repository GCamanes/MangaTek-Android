package com.groupany.mangatek.features.home.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import com.groupany.mangatek.core.helpers.NavHelper
import com.groupany.mangatek.core.presentation.components.EmptyError
import com.groupany.mangatek.core.presentation.screens.CustomError
import com.groupany.mangatek.core.presentation.components.MangaTekTitle
import com.groupany.mangatek.features.home.presentation.composables.HomeFilterFAB
import com.groupany.mangatek.features.home.presentation.composables.MangaLazyList
import com.groupany.mangatek.features.home.presentation.viewmodels.HomeViewModel
import com.groupany.ui.constants.UIConstants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController, viewModel: HomeViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val listState = rememberLazyListState()

    // Convert padding value to pixels (Int)
    val maxOffsetPx = with(LocalDensity.current) { UIConstants.PaddingMedium.roundToPx() }
    // Alpha value for top fading
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
                            modifier = Modifier.size(UIConstants.IconHeight),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
            )
        },
        floatingActionButton = {
            HomeFilterFAB(
                uiState.filter,
                modifier = Modifier.padding(bottom = UIConstants.PaddingMedium)
            )  { newFilter ->
                viewModel.updateFilter(newFilter)
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
                    else -> {
                        val mangaList = uiState.getFilteredList()
                        if (mangaList.isNotEmpty()) MangaLazyList(
                            state = listState,
                            mangaList = mangaList,
                            isFavorite = uiState::isFavorite,
                            onToggle = viewModel::toggleFavorite,
                            getCachedUrl = viewModel::getCachedUrl,
                            getDownloadUrl = viewModel::getDownloadUrl,
                        )
                        else EmptyError()
                    }
                }
                // Top gradient appearing on scroll
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(UIConstants.PaddingMedium)
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