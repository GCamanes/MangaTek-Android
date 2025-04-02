package com.groupany.mangatek.features.settings.presentation.composables

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextDecoration
import com.groupany.mangatek.core.presentation.composable.VerticalSpacer

@Composable
fun SettingsElement(
    title: String,
    content: @Composable () -> Unit
) {
    Text(
        title,
        style = MaterialTheme.typography.headlineSmall.copy(
            textDecoration = TextDecoration.Underline
        )
    )

    VerticalSpacer()

    content()
}