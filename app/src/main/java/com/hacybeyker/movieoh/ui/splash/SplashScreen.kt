package com.hacybeyker.movieoh.ui.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.hacybeyker.movieoh.R
import com.hacybeyker.uikit.theme.Mariner
import com.hacybeyker.uikit.theme.Montserrat
import com.hacybeyker.uikit.theme.White
import kotlinx.coroutines.delay

const val SPLASH_TIMER = 3000L

@Composable
fun SplashScreen(onFinished: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(SPLASH_TIMER)
        onFinished()
    }

    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .background(Mariner),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.movie))
            LottieAnimation(
                composition = composition,
                iterations = LottieConstants.IterateForever,
            )
            Text(
                text = stringResource(id = R.string.app_name),
                fontFamily = Montserrat,
                fontWeight = FontWeight.Bold,
                fontSize = 40.sp,
                color = White,
                modifier = Modifier.padding(top = 5.dp),
            )
        }
    }
}
