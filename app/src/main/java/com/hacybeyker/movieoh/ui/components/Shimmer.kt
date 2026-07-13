package com.hacybeyker.movieoh.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp

private const val SHIMMER_DURATION_MILLIS = 1_000
private const val SHIMMER_WIDTH = 400f

fun Modifier.shimmer(): Modifier =
    composed {
        val transition = rememberInfiniteTransition(label = "shimmer")
        val translateAnim by transition.animateFloat(
            initialValue = 0f,
            targetValue = 2_000f,
            animationSpec =
                infiniteRepeatable(
                    animation = tween(durationMillis = SHIMMER_DURATION_MILLIS, easing = LinearEasing),
                ),
            label = "shimmerTranslate",
        )
        val baseColor = MaterialTheme.colorScheme.surfaceVariant
        background(
            brush =
                Brush.linearGradient(
                    colors =
                        listOf(
                            baseColor.copy(alpha = 0.6f),
                            baseColor.copy(alpha = 0.2f),
                            baseColor.copy(alpha = 0.6f),
                        ),
                    start = Offset(translateAnim - SHIMMER_WIDTH, 0f),
                    end = Offset(translateAnim, 0f),
                ),
        )
    }

@Composable
fun ShimmerSection(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
        Box(
            modifier =
                Modifier
                    .padding(15.dp)
                    .fillMaxWidth(fraction = 0.4f)
                    .height(20.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .shimmer(),
        )
        Row(modifier = Modifier.padding(horizontal = 10.dp)) {
            repeat(4) {
                Box(
                    modifier =
                        Modifier
                            .padding(5.dp)
                            .size(width = POSTER_WIDTH, height = POSTER_HEIGHT)
                            .clip(RoundedCornerShape(POSTER_CORNER_RADIUS))
                            .shimmer(),
                )
            }
        }
    }
}

@Composable
fun ShimmerScreen(
    modifier: Modifier = Modifier,
    sections: Int = 4,
) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
    ) {
        repeat(sections) {
            ShimmerSection()
        }
    }
}
