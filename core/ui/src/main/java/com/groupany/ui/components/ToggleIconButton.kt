package com.groupany.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import com.groupany.ui.constants.UIConstants
import kotlinx.coroutines.launch

@Composable
fun ToggleIconButton(
    isSelected: Boolean,
    selectedIcon: ImageVector,
    unselectedIcon: ImageVector,
    contentDescription: String?,
    onToggle: suspend () -> Unit
) {
    val scale = remember { Animatable(1f) }
    var enabled by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()

    IconButton(
        onClick = {
            if (enabled) {
                scope.launch {
                    enabled = false
                    scale.animateTo(if (isSelected) 0.8f else 1.2f, animationSpec = tween(300))
                    scale.animateTo(1f, animationSpec = tween(100))
                    onToggle()
                    enabled = true
                }
            }
        },
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        modifier = Modifier.graphicsLayer(
            scaleX = scale.value,
            scaleY = scale.value
        )
    ) {
        Icon(
            if (isSelected) selectedIcon else unselectedIcon,
            contentDescription = contentDescription,
            modifier = Modifier.size(UIConstants.IconHeight),
            tint = MaterialTheme.colorScheme.onBackground
        )
    }
}
