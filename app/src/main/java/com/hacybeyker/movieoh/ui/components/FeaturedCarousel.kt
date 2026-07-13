package com.hacybeyker.movieoh.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.hacybeyker.movieoh.domain.entity.MovieEntity
import com.hacybeyker.movieoh.utils.extensions.toTmdbImageUrl
import com.hacybeyker.uikit.component.NetworkImage
import com.hacybeyker.uikit.theme.MovieOhGradients
import kotlinx.coroutines.delay
import kotlin.math.absoluteValue

private val CARD_CORNER_RADIUS = 12.dp
private val CARD_PEEK_PADDING = 40.dp
private val CARD_SPACING = 12.dp
private const val CARD_ASPECT_RATIO = 16f / 9f
private const val AUTO_SCROLL_DELAY_MILLIS = 4_000L
private const val AUTO_SCROLL_ANIMATION_MILLIS = 650
private const val MIN_PAGE_SCALE = 0.94f
private const val MIN_PAGE_ALPHA = 0.75f

@Composable
fun FeaturedCarousel(
    movies: List<MovieEntity>,
    onMovieClick: (MovieEntity) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (movies.isEmpty()) return

    // Start near the middle of a very large virtual page range, aligned so the first
    // real movie is centered, so the user can swipe either direction without ever
    // hitting an edge and seeing an empty peek.
    val startPage =
        remember(movies.size) {
            val middle = Int.MAX_VALUE / 2
            middle - (middle % movies.size)
        }
    val pagerState = rememberPagerState(initialPage = startPage, pageCount = { Int.MAX_VALUE })

    LaunchedEffect(pagerState, movies.size) {
        if (movies.size <= 1) return@LaunchedEffect
        while (true) {
            delay(AUTO_SCROLL_DELAY_MILLIS)
            if (!pagerState.isScrollInProgress) {
                pagerState.animateScrollToPage(
                    page = pagerState.currentPage + 1,
                    animationSpec =
                        tween(
                            durationMillis = AUTO_SCROLL_ANIMATION_MILLIS,
                            easing = FastOutSlowInEasing,
                        ),
                )
            }
        }
    }

    HorizontalPager(
        state = pagerState,
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = CARD_PEEK_PADDING),
        pageSpacing = CARD_SPACING,
    ) { page ->
        val movie = movies[page % movies.size]
        FeaturedCard(
            movie = movie,
            onClick = { onMovieClick(movie) },
            modifier =
                Modifier.graphicsLayer {
                    val pageOffset =
                        ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction)
                            .absoluteValue
                            .coerceIn(0f, 1f)
                    val scale = lerp(MIN_PAGE_SCALE, 1f, 1f - pageOffset)
                    scaleX = scale
                    scaleY = scale
                    alpha = lerp(MIN_PAGE_ALPHA, 1f, 1f - pageOffset)
                },
        )
    }
}

@Composable
private fun FeaturedCard(
    movie: MovieEntity,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .aspectRatio(CARD_ASPECT_RATIO)
                .clip(RoundedCornerShape(CARD_CORNER_RADIUS))
                .clickable(onClick = onClick),
    ) {
        NetworkImage(
            url = movie.backdropPath.toTmdbImageUrl(),
            contentDescription = movie.title,
            cornerRadius = CARD_CORNER_RADIUS,
            modifier = Modifier.fillMaxSize(),
        )
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
                    .background(MovieOhGradients.scrim())
                    .padding(12.dp),
        ) {
            Text(
                text = movie.title,
                style = MaterialTheme.typography.titleMedium,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}
