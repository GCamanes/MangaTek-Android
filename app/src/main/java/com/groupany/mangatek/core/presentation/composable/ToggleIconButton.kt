package com.groupany.mangatek.core.presentation.composable

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.groupany.mangatek.core.constants.AppDimension

@Composable
fun ToggleIconButton(
    isSelected: Boolean,
    selectedIcon: ImageVector,
    unselectedIcon: ImageVector,
    contentDescription: String?,
    onToggle: () -> Unit
) {
    IconButton(onClick = { onToggle() }) {
        Icon(
            if (isSelected) selectedIcon else unselectedIcon,
            contentDescription = contentDescription,
            modifier = Modifier.size(AppDimension.IconHeight),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
