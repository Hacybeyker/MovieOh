package com.hacybeyker.movieoh.ui.home

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
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
import com.hacybeyker.movieoh.ui.components.FeaturedCarousel
import com.hacybeyker.movieoh.ui.components.MovieCarousel
import com.hacybeyker.movieoh.ui.components.ShimmerHomeScreen
import com.hacybeyker.movieoh.ui.movieactions.MovieActionsBottomSheet
import com.hacybeyker.uikit.component.GradientButton
import com.hacybeyker.uikit.component.SectionHeader

private enum class HomeViewState { LOADING, ERROR, CONTENT }

@Composable
fun HomeScreen(
    onMovieClick: (MovieEntity) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    HomeContent(
        uiState = uiState,
        onMovieClick = onMovieClick,
        onRetry = viewModel::loadHome,
    )
}

@Composable
fun HomeContent(
    uiState: HomeUiState,
    onMovieClick: (MovieEntity) -> Unit,
    onRetry: () -> Unit = {},
) {
    val modifier =
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .safeDrawingPadding()

    val viewState =
        when {
            uiState.isLoading -> HomeViewState.LOADING
            uiState.isError -> HomeViewState.ERROR
            else -> HomeViewState.CONTENT
        }

    var selectedMovie by remember { mutableStateOf<MovieEntity?>(null) }

    Crossfade(targetState = viewState, modifier = modifier, label = "home-view-state") { state ->
        when (state) {
            HomeViewState.LOADING -> ShimmerHomeScreen(modifier = Modifier.fillMaxSize())
            HomeViewState.ERROR -> HomeError(modifier = Modifier.fillMaxSize(), onRetry = onRetry)
            HomeViewState.CONTENT ->
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 24.dp),
                ) {
                    if (uiState.featuredMovies.isNotEmpty()) {
                        item(key = R.string.upcoming) {
                            FeaturedCarousel(
                                movies = uiState.featuredMovies,
                                onMovieClick = onMovieClick,
                                onMovieLongClick = { selectedMovie = it },
                            )
                        }
                    }
                    uiState.sections.forEach { section ->
                        item(key = section.titleRes) {
                            SectionHeader(title = stringResource(id = section.titleRes))
                            MovieCarousel(
                                movies = section.movies,
                                onMovieClick = onMovieClick,
                                onMovieLongClick = { selectedMovie = it },
                            )
                        }
                    }
                }
        }
    }

    selectedMovie?.let { movie ->
        MovieActionsBottomSheet(movie = movie, onDismiss = { selectedMovie = null })
    }
}

@Composable
private fun HomeError(
    modifier: Modifier = Modifier,
    onRetry: () -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(id = R.string.home_error),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Spacer(modifier = Modifier.height(16.dp))
        GradientButton(
            text = stringResource(id = R.string.retry),
            onClick = onRetry,
        )
    }
}
