package com.groupany.manga.presentation.screens

import androidx.compose.animation.EnterExitState
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.groupany.localization.R
import com.groupany.manga.presentation.components.MangaHeader
import com.groupany.manga.presentation.viewmodels.MangaDetailViewModel
import com.groupany.ui.SizeTools
import com.groupany.ui.animation.AnimationUtils.LocalNavAnimatedVisibilityScope
import com.groupany.ui.animation.AnimationUtils.LocalSharedTransitionScope
import com.groupany.ui.animation.AnimationUtils.boundsTransform
import com.groupany.ui.animation.AnimationUtils.nonSpatialExpressiveSpring
import com.groupany.ui.components.CustomTopAppBar
import com.groupany.ui.components.ToggleIconButton
import com.groupany.ui.constants.UIConstants

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun MangaDetailScreen(
    id: String,
    title: String,
    onBack: () -> Unit,
    viewModel: MangaDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberLazyListState()

    // containerSize is IntSize in pixels → convert to dp
    val screenWidth = SizeTools.getScreenWidth()
    val backgroundImageHeight = screenWidth / 0.68f
    val headerHeight =
        backgroundImageHeight - SizeTools.getTopAppBarHeight() - SizeTools.getStatusBarHeight()


    // Part for shared bounds animation when navigating (from list screen for example)
    val sharedTransitionScope = LocalSharedTransitionScope.current
        ?: throw IllegalStateException("No Scope found")
    val animatedVisibilityScope = LocalNavAnimatedVisibilityScope.current
        ?: throw IllegalStateException("No Scope found")

    with(sharedTransitionScope) {
        val roundedCornerAnim by animatedVisibilityScope.transition
            .animateDp(label = "rounded corner") { enterExit: EnterExitState ->
                when (enterExit) {
                    EnterExitState.PreEnter -> UIConstants.CornerRound
                    EnterExitState.Visible -> 0.dp
                    EnterExitState.PostExit -> UIConstants.CornerRound
                }
            }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .sharedBounds(
                    sharedTransitionScope.rememberSharedContentState(key = id),
                    animatedVisibilityScope = animatedVisibilityScope,
                    boundsTransform = boundsTransform,
                    clipInOverlayDuringTransition = OverlayClip(
                        RoundedCornerShape(roundedCornerAnim),
                    ),
                    exit = fadeOut(nonSpatialExpressiveSpring()),
                    enter = fadeIn(nonSpatialExpressiveSpring()),
                )
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(uiState.coverUrl)
                    .crossfade(300)
                    .build(),
                contentDescription = title,
                modifier = Modifier
                    .width(screenWidth)
                    .height(backgroundImageHeight), // same result as aspectRatio(0.7f)
                contentScale = ContentScale.Crop
            )

            Scaffold(
                containerColor = Color.Transparent,
                topBar = {
                    Box {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .windowInsetsTopHeight(WindowInsets.statusBars)
                                .background(MaterialTheme.colorScheme.background.copy(alpha = 0.5f))
                        )

                        CustomTopAppBar(
                            actions = {
                                ToggleIconButton(
                                    isSelected = uiState.isFavorite,
                                    selectedIcon = Icons.Outlined.Favorite,
                                    unselectedIcon = Icons.Outlined.FavoriteBorder,
                                    contentDescription = "add to favorites"
                                ) { viewModel.toggleFavorite(id) }
                            },
                            onBack = onBack,
                        )
                    }
                },
            ) { paddingValues ->
                Box(modifier = Modifier.padding(paddingValues)) {
                    LazyColumn(
                        state = scrollState,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        item {
                            MangaHeader(
                                height = headerHeight,
                                title = title,
                                manga = uiState.manga,
                            )

                            Text(
                                stringResource(R.string.chapters),
                                modifier = Modifier.padding(horizontal = UIConstants.PaddingMedium),
                                style = MaterialTheme.typography.titleLarge.copy(
                                    color = MaterialTheme.colorScheme.onBackground,
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}