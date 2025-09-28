package com.groupany.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
    title: @Composable (() -> Unit) = {},
    actions: @Composable RowScope.() -> Unit = {},
    containerColor: Color? = null,
    scrolledContainerColor: Color? = null,
    navigationIconContentColor: Color? = null,
    titleContentColor: Color? = null,
    actionIconContentColor: Color = Color.Unspecified,
    subtitleContentColor: Color = Color.Unspecified,
    onBack: (() -> Unit)? = null
) {
    TopAppBar(
        title = title,
        navigationIcon = {
            if (onBack != null) {
                CustomBackButton(onClick = onBack)
            } else null
        },
        actions = actions,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = containerColor ?: Color.Transparent,
            scrolledContainerColor = scrolledContainerColor ?: Color.Transparent,
            navigationIconContentColor = navigationIconContentColor
                ?: MaterialTheme.colorScheme.onBackground,
            titleContentColor = titleContentColor ?: MaterialTheme.colorScheme.onBackground,
            actionIconContentColor = actionIconContentColor,
            subtitleContentColor = subtitleContentColor,
        ),
    )
}