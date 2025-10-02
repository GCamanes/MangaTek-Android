package com.groupany.manga.presentation.components.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.Dp
import com.groupany.ui.SizeTools

@Composable
fun MangaAppBarGradient(
    height: Dp,
    alpha: Float = 0f,
    secondAlpha: Float = 0f,
) {
    val startAlpha = 0.5f
    val background = MaterialTheme.colorScheme.background
    val statusBarPx = SizeTools.getStatusBarHeight() / height

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .background(
                Brush.verticalGradient(
                    colorStops = arrayOf(
                        0.0f to background.copy(alpha = startAlpha + (1 - startAlpha) * alpha),
                        statusBarPx to background.copy(alpha = startAlpha + (1 - startAlpha) * alpha),
                        1f to background.copy(alpha = secondAlpha),
                    )
                )
            )
    )
}
