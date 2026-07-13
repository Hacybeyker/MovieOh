package com.hacybeyker.movieoh.ui.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hacybeyker.movieoh.R
import com.hacybeyker.movieoh.domain.entity.MovieEntity
import com.hacybeyker.movieoh.ui.components.MoviePoster
import com.hacybeyker.movieoh.ui.components.ShimmerScreen

@Composable
fun FavoritesScreen(
    onMovieClick: (MovieEntity) -> Unit,
    viewModel: FavoritesViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    FavoritesContent(
        uiState = uiState,
        onMovieClick = onMovieClick,
    )
}

@Composable
fun FavoritesContent(
    uiState: FavoritesUiState,
    onMovieClick: (MovieEntity) -> Unit,
) {
    val modifier =
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .safeDrawingPadding()

    when {
        uiState.isLoading -> ShimmerScreen(modifier = modifier, sections = 2)
        uiState.movies.isEmpty() -> FavoritesEmpty(modifier = modifier)
        else ->
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 109.dp),
                modifier = modifier,
                contentPadding = PaddingValues(10.dp),
            ) {
                items(uiState.movies, key = { it.id }) { movie ->
                    MoviePoster(
                        posterPath = movie.posterPath,
                        contentDescription = movie.title,
                        onClick = { onMovieClick(movie) },
                    )
                }
            }
    }
}

@Composable
private fun FavoritesEmpty(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(id = R.string.favorites_empty),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
        )
    }
}
