package com.groupany.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun ScreenTitle(
    title: String,
    color: Color? = null,
) {
    Text(
        title,
        style = MaterialTheme.typography.titleLarge.copy(
            color = color ?: MaterialTheme.colorScheme.onBackground,
        )
    )
}