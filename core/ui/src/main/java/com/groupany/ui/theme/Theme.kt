package com.groupany.ui.theme

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
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = OnBackgroundColor,
    onSurface = OnSurfaceColor,
    onTertiary = OnSurfaceVariantColor,
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