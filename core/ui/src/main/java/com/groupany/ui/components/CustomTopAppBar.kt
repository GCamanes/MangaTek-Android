package com.groupany.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
    title: @Composable (() -> Unit) = {},
    actions: @Composable RowScope.() -> Unit = {},
    colors: TopAppBarColors? = null,
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
        colors = colors ?: TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            titleContentColor = MaterialTheme.colorScheme.onBackground,
            scrolledContainerColor = Color.Transparent,
            navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
        ),
    )
}