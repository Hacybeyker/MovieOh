package com.hacybeyker.movieoh.ui.favorites

import androidx.compose.animation.Crossfade
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.hacybeyker.movieoh.ui.movieactions.MovieActionsBottomSheet

private enum class FavoritesViewState { LOADING, EMPTY, CONTENT }

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

    val viewState =
        when {
            uiState.isLoading -> FavoritesViewState.LOADING
            uiState.movies.isEmpty() -> FavoritesViewState.EMPTY
            else -> FavoritesViewState.CONTENT
        }

    var selectedMovie by remember { mutableStateOf<MovieEntity?>(null) }

    Crossfade(targetState = viewState, modifier = modifier, label = "favorites-view-state") { state ->
        when (state) {
            FavoritesViewState.LOADING -> ShimmerScreen(modifier = Modifier.fillMaxSize(), sections = 2)
            FavoritesViewState.EMPTY -> FavoritesEmpty(modifier = Modifier.fillMaxSize())
            FavoritesViewState.CONTENT ->
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 109.dp),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(10.dp),
                ) {
                    items(uiState.movies, key = { it.id }) { movie ->
                        MoviePoster(
                            posterPath = movie.posterPath,
                            contentDescription = movie.title,
                            onClick = { onMovieClick(movie) },
                            onLongClick = { selectedMovie = movie },
                        )
                    }
                }
        }
    }

    selectedMovie?.let { movie ->
        MovieActionsBottomSheet(movie = movie, onDismiss = { selectedMovie = null })
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
