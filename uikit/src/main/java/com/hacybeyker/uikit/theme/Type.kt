package com.hacybeyker.uikit.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.hacybeyker.uikit.R

val Montserrat =
    FontFamily(
        Font(R.font.montserrat_thin, FontWeight.Thin),
        Font(R.font.montserrat_light, FontWeight.Light),
        Font(R.font.montserrat_regular, FontWeight.Normal),
        Font(R.font.montserrat_bold, FontWeight.Bold),
    )

private val baseline = Typography()

val MovieOhTypography =
    Typography(
        displayLarge = baseline.displayLarge.copy(fontFamily = Montserrat),
        displayMedium = baseline.displayMedium.copy(fontFamily = Montserrat),
        displaySmall = baseline.displaySmall.copy(fontFamily = Montserrat),
        headlineLarge = baseline.headlineLarge.copy(fontFamily = Montserrat),
        headlineMedium = baseline.headlineMedium.copy(fontFamily = Montserrat),
        headlineSmall = baseline.headlineSmall.copy(fontFamily = Montserrat),
        titleLarge =
            baseline.titleLarge.copy(
                fontFamily = Montserrat,
                fontWeight = FontWeight.Normal,
                fontSize = 18.sp,
                letterSpacing = 0.02.em,
            ),
        titleMedium =
            baseline.titleMedium.copy(
                fontFamily = Montserrat,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                letterSpacing = 0.02.em,
            ),
        titleSmall = baseline.titleSmall.copy(fontFamily = Montserrat),
        bodyLarge = baseline.bodyLarge.copy(fontFamily = Montserrat),
        bodyMedium =
            baseline.bodyMedium.copy(
                fontFamily = Montserrat,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                letterSpacing = 0.02.em,
            ),
        bodySmall =
            baseline.bodySmall.copy(
                fontFamily = Montserrat,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                letterSpacing = 0.02.em,
            ),
        labelLarge = baseline.labelLarge.copy(fontFamily = Montserrat),
        labelMedium = baseline.labelMedium.copy(fontFamily = Montserrat),
        labelSmall = baseline.labelSmall.copy(fontFamily = Montserrat),
    )
