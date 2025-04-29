package com.groupany.mangatek.features.settings.presentation.composables

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.groupany.ui.components.VerticalSpacer

@Composable
fun SettingsElement(
    title: String,
    content: @Composable () -> Unit
) {
    Text(
        title,
        style = MaterialTheme.typography.headlineSmall
    )

    VerticalSpacer()

    content()
}