package com.hacybeyker.uikit.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

object MovieOhGradients {
    val accent =
        Brush.linearGradient(
            colors = listOf(CinematicPurple, CinematicMagenta),
        )

    val splashBackground =
        Brush.verticalGradient(
            colors = listOf(CinematicPurple, CinematicBackground),
        )

    fun scrim(baseColor: Color = CinematicBackground): Brush =
        Brush.verticalGradient(
            colors = listOf(Color.Transparent, baseColor.copy(alpha = 0.9f)),
        )
}
