package com.groupany.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.groupany.ui.constants.UIConstants

@Composable
fun ScrollGradient(
    modifier: Modifier = Modifier,
    alpha: Float = 0f
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(UIConstants.PaddingMedium)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.background.copy(alpha = alpha),
                        Color.Transparent,
                        Color.Transparent,
                    )
                )
            )
    )
}