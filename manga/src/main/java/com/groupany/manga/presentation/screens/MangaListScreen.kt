package com.groupany.manga.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.groupany.manga.presentation.components.MangaFilterFAB
import com.groupany.manga.presentation.components.MangaLazyList
import com.groupany.manga.presentation.viewmodels.MangaListViewModel
import com.groupany.ui.components.CustomError
import com.groupany.ui.components.EmptyError
import com.groupany.ui.components.MangaTekTitle
import com.groupany.ui.constants.UIConstants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MangaListScreen(
    actions: @Composable RowScope.() -> Unit = {},
    onMangaClick: (id: String, title: String, coverUrl: String) -> Unit,
    viewModel: MangaListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val listState = rememberLazyGridState()

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
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                ),
                actions = actions,
            )
        },
        floatingActionButton = {
            MangaFilterFAB(
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
                            onMangaClick = onMangaClick,
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
                                    MaterialTheme.colorScheme.background,
                                    Color.Transparent,
                                    Color.Transparent,
                                )
                            )
                        )
                        .align(Alignment.TopCenter)
                )
            }
    }
}