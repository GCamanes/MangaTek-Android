package com.groupany.manga.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import com.groupany.manga.domain.enums.MangaFilter
import com.groupany.ui.constants.UIConstants

@Composable
fun MangaFilterFAB(
    filter: MangaFilter,
    modifier: Modifier = Modifier,
    onClick: (MangaFilter) -> Unit,
) {
    var isFabExpanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.BottomCenter
    ) {
        SmallFAB(
            visible = isFabExpanded,
            selected = filter == MangaFilter.FAVORITES,
            icon = Icons.Default.Favorite,
            description = "Favorites",
            index = 1,
        ) {
            isFabExpanded = false
            onClick(MangaFilter.FAVORITES)
        }

        SmallFAB(
            visible = isFabExpanded,
            selected = filter == MangaFilter.ALL,
            icon = Icons.Default.Menu,
            description = "All",
        ) {
            isFabExpanded = false
            onClick(MangaFilter.ALL)
        }

        FloatingActionButton(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(UIConstants.ButtonHeight),
            onClick = { isFabExpanded = !isFabExpanded }
        ) {
            Icon(
                imageVector = if (isFabExpanded) Icons.Default.Close else Icons.Default.FilterList,
                contentDescription = "Toggle Filter",
                Modifier.size(UIConstants.IconHeight)
            )
        }
    }
}

@Composable
private fun SmallFAB(
   visible: Boolean,
   selected: Boolean,
   icon: ImageVector,
   description: String,
   index: Int = 0,
   onClick: () -> Unit,
) {
    val initialDp = UIConstants.ButtonHeight + UIConstants.PaddingMedium
    val variableDp = (UIConstants.SmallButtonHeight + UIConstants.PaddingMedium) * index
    val totalDp = initialDp + variableDp

    val density = LocalDensity.current
    val totalOffsetPx = with(density) { totalDp.roundToPx() }

    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(initialOffsetY = { totalOffsetPx }) + fadeIn(),
        exit = slideOutVertically(targetOffsetY = { totalOffsetPx }) + fadeOut(),
        modifier = Modifier.padding(bottom = totalDp)
    ) {
        SmallFloatingActionButton(
            modifier = Modifier.size(UIConstants.SmallButtonHeight),
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = if (selected) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.onSurface,
            onClick = { onClick() },
        ) {
            Icon(
                imageVector = icon,
                contentDescription = description,
                Modifier.size(UIConstants.IconHeight)
            )
        }
    }
}