package com.groupany.settings.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.groupany.ui.components.CustomSpacerSize
import com.groupany.ui.components.VerticalSpacer
import com.groupany.ui.constants.UIConstants

@Composable
fun SettingsElement(
    title: String,
    content: @Composable () -> Unit
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(UIConstants.PaddingMedium)) {
        Text(
            title,
            style = MaterialTheme.typography.headlineSmall.copy(color = MaterialTheme.colorScheme.onSurface)
        )

        VerticalSpacer(CustomSpacerSize.SMALL)

        content()
    }
}