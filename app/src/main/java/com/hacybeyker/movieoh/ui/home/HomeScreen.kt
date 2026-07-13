package com.hacybeyker.movieoh.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
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
import com.hacybeyker.movieoh.ui.components.FeaturedCarousel
import com.hacybeyker.movieoh.ui.components.MovieCarousel
import com.hacybeyker.movieoh.ui.components.ShimmerScreen
import com.hacybeyker.uikit.component.GradientButton
import com.hacybeyker.uikit.component.SectionHeader

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

    when {
        uiState.isLoading -> ShimmerScreen(modifier = modifier)
        uiState.isError -> HomeError(modifier = modifier, onRetry = onRetry)
        else ->
            LazyColumn(modifier = modifier) {
                if (uiState.featuredMovies.isNotEmpty()) {
                    item(key = R.string.upcoming) {
                        FeaturedCarousel(
                            movies = uiState.featuredMovies,
                            onMovieClick = onMovieClick,
                        )
                    }
                }
                uiState.sections.forEach { section ->
                    item(key = section.titleRes) {
                        SectionHeader(title = stringResource(id = section.titleRes))
                        MovieCarousel(
                            movies = section.movies,
                            onMovieClick = onMovieClick,
                        )
                    }
                }
            }
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
