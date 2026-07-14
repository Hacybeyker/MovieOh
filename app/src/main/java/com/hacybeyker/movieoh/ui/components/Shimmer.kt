package com.hacybeyker.movieoh.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
private const val GRID_PLACEHOLDER_COUNT = 12
private const val MOVIE_BACKDROP_HEIGHT = 260
private const val HOME_SECTION_COUNT = 3
private const val TEXT_LINE_COUNT = 3

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
private fun ShimmerTitleBar(modifier: Modifier = Modifier) {
    Box(
        modifier =
            modifier
                .padding(15.dp)
                .fillMaxWidth(fraction = 0.4f)
                .height(20.dp)
                .clip(RoundedCornerShape(5.dp))
                .shimmer(),
    )
}

@Composable
private fun ShimmerPoster(modifier: Modifier = Modifier) {
    Box(
        modifier =
            modifier
                .padding(5.dp)
                .size(width = POSTER_WIDTH, height = POSTER_HEIGHT)
                .clip(RoundedCornerShape(POSTER_CORNER_RADIUS))
                .shimmer(),
    )
}

@Composable
private fun ShimmerPosterRow(modifier: Modifier = Modifier) {
    Row(modifier = modifier.padding(horizontal = 10.dp)) {
        repeat(4) {
            ShimmerPoster()
        }
    }
}

@Composable
fun ShimmerSection(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
        ShimmerTitleBar()
        ShimmerPosterRow()
    }
}

// Mirrors FeaturedCarousel: a big 16:9 card with the previous/next movie peeking at the edges.
@Composable
private fun ShimmerFeaturedCarousel(modifier: Modifier = Modifier) {
    val peekWidth = CARD_PEEK_PADDING - CARD_SPACING
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.spacedBy(CARD_SPACING),
    ) {
        Box(
            modifier =
                Modifier
                    .width(peekWidth)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(CARD_CORNER_RADIUS))
                    .shimmer(),
        )
        Box(
            modifier =
                Modifier
                    .weight(1f)
                    .aspectRatio(CARD_ASPECT_RATIO)
                    .clip(RoundedCornerShape(CARD_CORNER_RADIUS))
                    .shimmer(),
        )
        Box(
            modifier =
                Modifier
                    .width(peekWidth)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(CARD_CORNER_RADIUS))
                    .shimmer(),
        )
    }
}

@Composable
fun ShimmerHomeScreen(modifier: Modifier = Modifier) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
    ) {
        ShimmerFeaturedCarousel()
        repeat(HOME_SECTION_COUNT) {
            ShimmerSection()
        }
    }
}

@Composable
fun ShimmerGridScreen(modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = POSTER_WIDTH),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(10.dp),
        userScrollEnabled = false,
    ) {
        items(GRID_PLACEHOLDER_COUNT) {
            ShimmerPoster()
        }
    }
}

@Composable
fun ShimmerMovieScreen(modifier: Modifier = Modifier) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(MOVIE_BACKDROP_HEIGHT.dp)
                    .shimmer(),
        )
        Row(modifier = Modifier.padding(15.dp)) {
            ShimmerPoster()
            Column(modifier = Modifier.padding(start = 10.dp, top = 5.dp)) {
                Box(
                    modifier =
                        Modifier
                            .fillMaxWidth(fraction = 0.7f)
                            .height(20.dp)
                            .clip(RoundedCornerShape(5.dp))
                            .shimmer(),
                )
                Box(
                    modifier =
                        Modifier
                            .padding(top = 10.dp)
                            .fillMaxWidth(fraction = 0.4f)
                            .height(16.dp)
                            .clip(RoundedCornerShape(5.dp))
                            .shimmer(),
                )
            }
        }
        ShimmerTitleBar()
        repeat(TEXT_LINE_COUNT) {
            Box(
                modifier =
                    Modifier
                        .padding(horizontal = 15.dp, vertical = 4.dp)
                        .fillMaxWidth()
                        .height(14.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .shimmer(),
            )
        }
        ShimmerTitleBar()
        ShimmerPosterRow()
    }
}
