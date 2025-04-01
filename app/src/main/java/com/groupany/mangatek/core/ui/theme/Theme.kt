package com.groupany.mangatek.core.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryColor,
    secondary = SecondaryColor,
    tertiary = TertiaryColor,
    background = BackgroundColor,
    surface = SurfaceColor,
    surfaceVariant = SurfaceVariantColor,
    onPrimary = BackgroundColor,
    onSecondary = BackgroundColor,
    onBackground = Color.White,
    onSurface = Color.White,
    onTertiary = Color.White,
)

@Composable
fun MangaTekTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = MangaTekTypography,
        content = content
    )
}