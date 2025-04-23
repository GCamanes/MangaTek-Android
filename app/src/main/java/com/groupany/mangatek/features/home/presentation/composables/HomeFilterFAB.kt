package com.groupany.mangatek.features.home.presentation.composables

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
import com.groupany.mangatek.core.constants.AppDimension
import com.groupany.mangatek.features.home.data.enums.HomeFilter

@Composable
fun HomeFilterFAB(
    filter: HomeFilter,
    modifier: Modifier = Modifier,
    onClick: (HomeFilter) -> Unit,
) {
    var isFabExpanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.BottomCenter
    ) {
        SmallFAB(
            visible = isFabExpanded,
            selected = filter == HomeFilter.FAVORITES,
            icon = Icons.Default.Favorite,
            description = "Favorites",
            index = 1,
        ) {
            isFabExpanded = false
            onClick(HomeFilter.FAVORITES)
        }

        SmallFAB(
            visible = isFabExpanded,
            selected = filter == HomeFilter.ALL,
            icon = Icons.Default.Menu,
            description = "All",
        ) {
            isFabExpanded = false
            onClick(HomeFilter.ALL)
        }

        FloatingActionButton(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size( AppDimension.ButtonHeight),
            onClick = { isFabExpanded = !isFabExpanded }
        ) {
            Icon(
                imageVector = if (isFabExpanded) Icons.Default.Close else Icons.Default.FilterList,
                contentDescription = "Toggle Filter",
                Modifier.size(AppDimension.IconHeight)
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
    val initialDp = AppDimension.ButtonHeight + AppDimension.PaddingMedium
    val variableDp = (AppDimension.SmallButtonHeight + AppDimension.PaddingMedium) * index
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
            modifier = Modifier.size(AppDimension.SmallButtonHeight),
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = if (selected) MaterialTheme.colorScheme.secondary
            else MaterialTheme.colorScheme.primary,
            onClick = { onClick() },
        ) {
            Icon(
                imageVector = icon,
                contentDescription = description,
                Modifier.size(AppDimension.IconHeight)
            )
        }
    }
}