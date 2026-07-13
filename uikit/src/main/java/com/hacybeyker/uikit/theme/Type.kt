package com.hacybeyker.uikit.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
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

val MovieOhTypography =
    Typography(
        titleLarge =
            TextStyle(
                fontFamily = Montserrat,
                fontWeight = FontWeight.Normal,
                fontSize = 18.sp,
                letterSpacing = 0.02.em,
            ),
        titleMedium =
            TextStyle(
                fontFamily = Montserrat,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                letterSpacing = 0.02.em,
            ),
        bodyMedium =
            TextStyle(
                fontFamily = Montserrat,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                letterSpacing = 0.02.em,
            ),
        bodySmall =
            TextStyle(
                fontFamily = Montserrat,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                letterSpacing = 0.02.em,
            ),
    )
