package com.hacybeyker.movieoh.ui.components

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.hacybeyker.movieoh.domain.entity.MovieEntity
import com.hacybeyker.movieoh.utils.extensions.toTmdbImageUrl
import com.hacybeyker.uikit.component.NetworkImage
import kotlinx.coroutines.delay

private val CARD_CORNER_RADIUS = 12.dp
private const val CARD_ASPECT_RATIO = 16f / 9f
private const val AUTO_SCROLL_DELAY_MILLIS = 4_000L

@Composable
fun FeaturedCarousel(
    movies: List<MovieEntity>,
    onMovieClick: (MovieEntity) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (movies.isEmpty()) return

    val pagerState = rememberPagerState(pageCount = { movies.size })

    LaunchedEffect(pagerState, movies.size) {
        if (movies.size <= 1) return@LaunchedEffect
        while (true) {
            delay(AUTO_SCROLL_DELAY_MILLIS)
            if (!pagerState.isScrollInProgress) {
                val nextPage = (pagerState.currentPage + 1) % movies.size
                pagerState.animateScrollToPage(nextPage)
            }
        }
    }

    HorizontalPager(
        state = pagerState,
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 32.dp),
        pageSpacing = 12.dp,
    ) { page ->
        val movie = movies[page]
        FeaturedCard(
            movie = movie,
            onClick = { onMovieClick(movie) },
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
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.75f)),
                        ),
                    ).padding(12.dp),
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
