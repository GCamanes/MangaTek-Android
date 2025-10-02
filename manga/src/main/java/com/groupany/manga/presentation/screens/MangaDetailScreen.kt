package com.groupany.manga.presentation.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.EnterExitState
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.groupany.localization.R
import com.groupany.manga.presentation.components.detail.MangaAppBarGradient
import com.groupany.manga.presentation.components.detail.MangaChaptersRow
import com.groupany.manga.presentation.components.detail.MangaHeader
import com.groupany.manga.presentation.viewmodels.MangaDetailViewModel
import com.groupany.ui.SizeTools
import com.groupany.ui.animation.AnimationUtils.LocalNavAnimatedVisibilityScope
import com.groupany.ui.animation.AnimationUtils.LocalSharedTransitionScope
import com.groupany.ui.animation.AnimationUtils.boundsTransform
import com.groupany.ui.animation.AnimationUtils.isAnimationFinished
import com.groupany.ui.animation.AnimationUtils.nonSpatialExpressiveSpring
import com.groupany.ui.components.CustomSpacerSize
import com.groupany.ui.components.CustomTopAppBar
import com.groupany.ui.components.ScreenTitle
import com.groupany.ui.components.ScrollGradient
import com.groupany.ui.components.ToggleIconButton
import com.groupany.ui.components.VerticalSpacer
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
    val manga = uiState.manga

    val screenWidth = SizeTools.getScreenWidth()
    val backgroundImageHeight = screenWidth / 0.68f
    val headerHeight = backgroundImageHeight
    val headerHeightPx = SizeTools.convertDpToPx(headerHeight)

    // Scroll logic
    val scrollState = rememberLazyListState()
    val appBarPx = with(LocalDensity.current) { SizeTools.getFullAppBarHeight().toPx() }
    val scrollOffset by remember {
        derivedStateOf {
            val firstVisibleItemIndex = scrollState.firstVisibleItemIndex
            val firstVisibleItemOffset = scrollState.firstVisibleItemScrollOffset
            firstVisibleItemIndex * headerHeightPx + firstVisibleItemOffset
        }
    }

    LaunchedEffect(scrollState) {
        snapshotFlow { scrollOffset }
            .collect { offset ->
                viewModel.updateUI(offset, appBarPx)
            }
    }

    // Part for shared bounds animation
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
                containerColor = MaterialTheme.colorScheme.background.copy(alpha = uiState.alpha),
                contentWindowInsets = WindowInsets(0.dp),
                topBar = {
                    Box {
                        MangaAppBarGradient(
                            modifier = Modifier.matchParentSize(),
                            uiState.alpha,
                            uiState.secondAlpha,
                        )

                        CustomTopAppBar(
                            title = {
                                AnimatedContent(targetState = uiState.switchTitle) { target ->
                                    if (target) {
                                        ScreenTitle(
                                            title = title,
                                            centered = true,
                                            modifier = Modifier
                                                .sharedBounds(
                                                    sharedTransitionScope.rememberSharedContentState(
                                                        key = "title-$id"
                                                    ),
                                                    animatedVisibilityScope = this@AnimatedContent
                                                ),
                                        )
                                    }
                                }
                            },
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
                Box(
                    modifier = Modifier.padding(
                        start = paddingValues.calculateStartPadding(LayoutDirection.Ltr),
                        end = paddingValues.calculateEndPadding(LayoutDirection.Ltr),
                        bottom = paddingValues.calculateBottomPadding()
                    )
                ) {
                    LazyColumn(
                        state = scrollState,
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        item {
                            MangaHeader(
                                height = headerHeight,
                                id = id,
                                title = title,
                                manga = manga,
                                showTitle = !uiState.switchTitle,
                                onTitleYChanged = { y ->
                                    viewModel.saveStableTitleY(
                                        y,
                                        animatedVisibilityScope
                                            .isAnimationFinished()
                                    )
                                },
                            )
                        }
                        item {
                            Text(
                                stringResource(R.string.chapters),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(MaterialTheme.colorScheme.background)
                                    .padding(horizontal = UIConstants.PaddingMedium),
                                style = MaterialTheme.typography.titleLarge.copy(
                                    color = MaterialTheme.colorScheme.onBackground,
                                )
                            )
                        }
                        if (manga != null) {
                            val columns = 3
                            val chapterRows: List<List<String>> = manga.chapters.chunked(columns)

                            items(chapterRows.size) { index ->
                                MangaChaptersRow(
                                    columns = columns,
                                    manga = manga,
                                    chapters = chapterRows[index],
                                )
                            }

                            item { VerticalSpacer(CustomSpacerSize.BIG) }
                        }
                    }

                    ScrollGradient(
                        modifier = Modifier.padding(top = paddingValues.calculateTopPadding()),
                        alpha = uiState.secondAlpha
                    )
                }
            }
        }
    }
}