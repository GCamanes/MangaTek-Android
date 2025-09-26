package com.groupany.manga.presentation.screens

import androidx.compose.animation.EnterExitState
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.groupany.manga.presentation.viewmodels.MangaDetailViewModel
import com.groupany.ui.animation.AnimationUtils.LocalNavAnimatedVisibilityScope
import com.groupany.ui.animation.AnimationUtils.LocalSharedTransitionScope
import com.groupany.ui.animation.AnimationUtils.boundsTransformWithoutBounce
import com.groupany.ui.animation.AnimationUtils.nonSpatialExpressiveSpring
import com.groupany.ui.components.CustomBackButton
import com.groupany.ui.components.ScreenTitle
import com.groupany.ui.components.ToggleIconButton
import com.groupany.ui.constants.UIConstants

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun MangaDetailScreen(
    id: String,
    title: String,
    coverUrl: String,
    onBack: () -> Unit,
    viewModel: MangaDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

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
                    boundsTransform = boundsTransformWithoutBounce,
                    clipInOverlayDuringTransition = OverlayClip(
                        RoundedCornerShape(roundedCornerAnim),
                    ),
                    exit = fadeOut(nonSpatialExpressiveSpring()),
                    enter = fadeIn(nonSpatialExpressiveSpring()),
                )
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(coverUrl)
                    .crossfade(300)
                    .build(),
                contentDescription = title,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(0.7f),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(Color.Transparent, Color.Black.copy(alpha = 0.6f))
                        )
                    )
            )

            Scaffold(
                containerColor = Color.Transparent,
                topBar = {
                    TopAppBar(
                        title = { ScreenTitle(title = title) },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.Transparent,
                            scrolledContainerColor = Color.Transparent,
                            navigationIconContentColor = Color.White,
                            titleContentColor = Color.White
                        ),
                        actions = {
                            ToggleIconButton(
                                isSelected = uiState.isFavorite,
                                selectedIcon = Icons.Outlined.Favorite,
                                unselectedIcon = Icons.Outlined.FavoriteBorder,
                                contentDescription = "add to favorites"
                            ) { viewModel.toggleFavorite(id) }
                        },
                        navigationIcon = { CustomBackButton(onClick = onBack) },
                    )
                },
            ) { paddingValues ->
                Box(modifier = Modifier.padding(paddingValues)) {}
            }
        }
    }
}