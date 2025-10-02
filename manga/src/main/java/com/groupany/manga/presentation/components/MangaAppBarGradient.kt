package com.groupany.manga.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush

@Composable
fun MangaAppBarGradient(
    modifier: Modifier = Modifier,
    alpha: Float = 0f,
    secondAlpha: Float = 0f,
) {
    Box(
        modifier = modifier
            .background(
                Brush.verticalGradient(
                    colorStops = arrayOf(
                        0.0f to MaterialTheme.colorScheme.background.copy(alpha = 0.6f + 0.4f * alpha),
                        0.3f to MaterialTheme.colorScheme.background.copy(alpha = 0.6f + 0.4f * alpha),
                        1f to MaterialTheme.colorScheme.background.copy(alpha = secondAlpha),
                    )
                )
            )
    )
}
