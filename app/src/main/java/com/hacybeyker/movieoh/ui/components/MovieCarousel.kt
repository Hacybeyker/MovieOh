package com.hacybeyker.movieoh.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hacybeyker.movieoh.domain.entity.MovieEntity

@Composable
fun MovieCarousel(
    movies: List<MovieEntity>,
    onMovieClick: (MovieEntity) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 10.dp),
    ) {
        items(movies, key = { it.id }) { movie ->
            MoviePoster(
                posterPath = movie.posterPath,
                contentDescription = movie.title,
                onClick = { onMovieClick(movie) },
            )
        }
    }
}
