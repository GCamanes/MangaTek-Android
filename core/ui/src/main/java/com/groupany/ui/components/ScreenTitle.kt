package com.groupany.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign

@Composable
fun ScreenTitle(
    title: String,
    color: Color? = null,
    centered: Boolean = false,
    alpha: Float = 1f,
) {
    Text(
        title,
        maxLines = 2,
        modifier = Modifier
            .fillMaxWidth()
            .alpha(alpha),
        textAlign = if (centered) TextAlign.Center else TextAlign.Unspecified,
        style = MaterialTheme.typography.headlineSmall.copy(
            color = color ?: MaterialTheme.colorScheme.onBackground,
        )
    )
}