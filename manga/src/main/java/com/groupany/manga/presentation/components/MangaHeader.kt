package com.groupany.manga.presentation.components

import androidx.compose.animation.ExperimentalSharedTransitionApi
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import com.groupany.manga.domain.entities.MangaEntity
import com.groupany.ui.SizeTools
import com.groupany.ui.components.VerticalSpacer
import com.groupany.ui.constants.UIConstants

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MangaHeader(
    alpha: Float = 0f,
    titleAlpha: Float = 1f,
    height: Dp,
    title: String,
    manga: MangaEntity? = null,
    onTitleYChanged: (Float) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .height(height)
            .background(
                Brush.verticalGradient(
                    colorStops = arrayOf(
                        0.0f to MaterialTheme.colorScheme.background.copy(alpha = alpha),
                        0.8f to MaterialTheme.colorScheme.background, // still transparent
                        1f to MaterialTheme.colorScheme.background // fully solid
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
            val paddingDp = SizeTools.convertDpToPx(UIConstants.PaddingMedium)
            Text(
                text = title,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { layoutCoordinates ->
                        // Y position relative to parent / screen

                        onTitleYChanged(layoutCoordinates.positionInParent().y + paddingDp)
                    }
                    .alpha(titleAlpha),
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

                ReadChapterCard(
                    mangaId = manga.id,
                    chapterId = manga.chapters.first(),
                    onClick = { mid, cid -> }
                )
            }
        }
    }
}