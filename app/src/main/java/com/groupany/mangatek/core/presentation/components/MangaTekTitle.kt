package com.groupany.mangatek.core.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun MangaTekTitle() {
    Row {
        Text(
            "Manga",
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.primary,
        )
        Text(
            "Tek",
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.secondary,
        )
    }
}