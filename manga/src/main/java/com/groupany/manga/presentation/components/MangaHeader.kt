package com.groupany.manga.presentation.components

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import com.groupany.manga.domain.entities.MangaEntity
import com.groupany.ui.animation.AnimationUtils.LocalNavAnimatedVisibilityScope
import com.groupany.ui.animation.AnimationUtils.LocalSharedTransitionScope
import com.groupany.ui.animation.AnimationUtils.boundsTransform
import com.groupany.ui.animation.AnimationUtils.nonSpatialExpressiveSpring
import com.groupany.ui.components.VerticalSpacer
import com.groupany.ui.constants.UIConstants

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MangaHeader(
    height: Dp,
    title: String,
    manga: MangaEntity? = null,
) {
    // Part for shared bounds animation when navigating (from list screen for example)
    val sharedTransitionScope = LocalSharedTransitionScope.current
        ?: throw IllegalStateException("No Scope found")
    val animatedVisibilityScope = LocalNavAnimatedVisibilityScope.current
        ?: throw IllegalStateException("No Scope found")

    with(sharedTransitionScope) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .height(height)
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color.Transparent,
                            MaterialTheme.colorScheme.background
                        )
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(UIConstants.PaddingMedium),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom,
            ) {
                Text(
                    text = title,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .sharedBounds(
                            sharedTransitionScope
                                .rememberSharedContentState(key = "title-${manga?.id ?: ""}"),
                            animatedVisibilityScope = animatedVisibilityScope,
                            boundsTransform = boundsTransform,
                            exit = fadeOut(nonSpatialExpressiveSpring()),
                            enter = fadeIn(nonSpatialExpressiveSpring()),
                        ),
                    style = MaterialTheme.typography.displaySmall.copy(
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                )

                if (manga != null) {
                    VerticalSpacer()

                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(
                            UIConstants.PaddingSmall,
                            Alignment.CenterHorizontally,
                        ),
                        verticalArrangement = Arrangement.spacedBy(
                            UIConstants.PaddingSmall,
                        ),
                    ) {
                        manga.genres.map { genre ->
                            GenreTag(genre)
                        }
                    }

                    VerticalSpacer()
                    Text(
                        manga.getFilteredAuthors().toString(),
                        style = MaterialTheme.typography.headlineSmall.copy(
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    )
                }
            }
        }
    }
}