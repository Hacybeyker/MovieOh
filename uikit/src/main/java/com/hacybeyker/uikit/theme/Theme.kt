package com.hacybeyker.uikit.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme =
    lightColorScheme(
        primary = Mariner,
        background = White,
        onBackground = Umbra,
        surface = White,
        onSurface = Umbra,
        surfaceVariant = Paternoster,
        onSurfaceVariant = BlackTie,
        outline = Black,
    )

private val DarkColorScheme =
    darkColorScheme(
        primary = Mariner,
        background = Umbra,
        onBackground = Paternoster,
        surface = Umbra,
        onSurface = Paternoster,
        surfaceVariant = BlackTie,
        onSurfaceVariant = SilverSnippet,
        outline = White,
    )

@Composable
fun MovieOhTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        typography = MovieOhTypography,
        content = content,
    )
}
