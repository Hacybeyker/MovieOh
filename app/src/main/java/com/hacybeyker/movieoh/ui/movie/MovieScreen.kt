package com.hacybeyker.movieoh.ui.movie

import android.content.Intent
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hacybeyker.movieoh.R
import com.hacybeyker.movieoh.domain.entity.MovieEntity
import com.hacybeyker.movieoh.domain.entity.PlatformType
import com.hacybeyker.movieoh.domain.entity.PlatformsEntity
import com.hacybeyker.movieoh.ui.components.MovieCarousel
import com.hacybeyker.movieoh.ui.components.MoviePoster
import com.hacybeyker.movieoh.ui.components.ShimmerMovieScreen
import com.hacybeyker.movieoh.ui.movieactions.MovieActionsBottomSheet
import com.hacybeyker.movieoh.ui.movieactions.MovieActionsViewModel
import com.hacybeyker.movieoh.ui.movieactions.shareMovie
import com.hacybeyker.movieoh.utils.extensions.format
import com.hacybeyker.movieoh.utils.extensions.getRuntime
import com.hacybeyker.movieoh.utils.extensions.toTmdbImageUrl
import com.hacybeyker.movieoh.utils.extensions.toTmdbLogoUrl
import com.hacybeyker.uikit.component.GradientButton
import com.hacybeyker.uikit.component.NetworkImage
import com.hacybeyker.uikit.component.SectionHeader

private const val OVERVIEW_MAX_LINES = 4
private const val VOTE_AVERAGE_DECIMALS = 1
private const val RELEASE_YEAR_LENGTH = 4
private const val HERO_ASPECT_RATIO = 3f / 4f
private const val HERO_FADE_START = 0.6f
private val PLATFORM_LOGO_SIZE = 44.dp
private val PLATFORM_LOGO_CORNER_RADIUS = 10.dp

@Composable
fun MovieScreen(
    onMovieClick: (MovieEntity) -> Unit,
    onBackClick: () -> Unit,
    viewModel: MovieViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(uiState.platformsErrorMessage) {
        uiState.platformsErrorMessage?.let { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    MovieContent(
        uiState = uiState,
        onMovieClick = onMovieClick,
        onBackClick = onBackClick,
        onOpenLink = { url ->
            context.startActivity(Intent(Intent.ACTION_VIEW, url.toUri()))
        },
    )
}

@Composable
fun MovieContent(
    uiState: MovieUiState,
    onMovieClick: (MovieEntity) -> Unit,
    onBackClick: () -> Unit,
    onOpenLink: (String) -> Unit,
) {
    val backgroundModifier =
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)

    val movie = uiState.movie
    val hasContent = !uiState.isLoading && movie != null
    var selectedMovie by remember { mutableStateOf<MovieEntity?>(null) }

    Crossfade(targetState = hasContent, modifier = backgroundModifier, label = "movie-view-state") { showContent ->
        if (!showContent || movie == null) {
            ShimmerMovieScreen(modifier = Modifier.fillMaxSize())
        } else {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                ) {
                    MovieHero(movie = movie)
                    MovieDetail(
                        uiState = uiState,
                        onMovieClick = onMovieClick,
                        onMovieLongClick = { selectedMovie = it },
                        onOpenLink = onOpenLink,
                    )
                }
                IconButton(
                    onClick = onBackClick,
                    modifier =
                        Modifier
                            .align(Alignment.TopEnd)
                            .padding(end = 15.dp, top = 30.dp),
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(id = R.string.back),
                        tint = MaterialTheme.colorScheme.onBackground,
                    )
                }
            }
        }
    }

    selectedMovie?.let { selected ->
        MovieActionsBottomSheet(movie = selected, onDismiss = { selectedMovie = null })
    }
}

@Composable
private fun MovieHero(movie: MovieEntity) {
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .aspectRatio(HERO_ASPECT_RATIO),
    ) {
        NetworkImage(
            url = movie.posterPath.toTmdbImageUrl(),
            contentDescription = movie.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
        )
        Box(
            modifier =
                Modifier
                    .matchParentSize()
                    .background(
                        Brush.verticalGradient(
                            HERO_FADE_START to MaterialTheme.colorScheme.background.copy(alpha = 0f),
                            1f to MaterialTheme.colorScheme.background,
                        ),
                    ),
        )
    }
}

@Composable
private fun MovieDetail(
    uiState: MovieUiState,
    onMovieClick: (MovieEntity) -> Unit,
    onMovieLongClick: (MovieEntity) -> Unit,
    onOpenLink: (String) -> Unit,
) {
    val movie = checkNotNull(uiState.movie)
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = movie.title,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp),
        )
        Text(
            text = movie.metadataLine(),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp, vertical = 8.dp),
        )

        uiState.platforms.firstOrNull()?.let { primaryPlatform ->
            GradientButton(
                text = stringResource(id = R.string.play),
                onClick = { onOpenLink(primaryPlatform.url) },
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp, vertical = 5.dp),
            )
        }

        MovieActionsRow(movie = movie)

        Text(
            text = movie.overview,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = OVERVIEW_MAX_LINES,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp),
        )

        if (uiState.platforms.isNotEmpty()) {
            SectionHeader(title = stringResource(id = R.string.platforms), showIcon = false)
            PlatformsRow(platforms = uiState.platforms, onOpenLink = onOpenLink)
            Text(
                text = stringResource(id = R.string.justwatch_attribution),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp),
            )
        }

        if (uiState.cast.isNotEmpty()) {
            SectionHeader(title = stringResource(id = R.string.cast), showIcon = false)
            MovieCastRow(uiState = uiState)
        }

        if (uiState.similar.isNotEmpty()) {
            SectionHeader(title = stringResource(id = R.string.similar_movies), showIcon = false)
            MovieCarousel(
                movies = uiState.similar,
                onMovieClick = onMovieClick,
                onMovieLongClick = onMovieLongClick,
            )
        }
    }
}

@Composable
private fun PlatformsRow(
    platforms: List<PlatformsEntity>,
    onOpenLink: (String) -> Unit,
) {
    Row(
        modifier =
            Modifier
                .padding(horizontal = 15.dp, vertical = 5.dp)
                .horizontalScroll(rememberScrollState()),
    ) {
        platforms.forEach { platform ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(end = 12.dp),
            ) {
                NetworkImage(
                    url = platform.logoPath.toTmdbLogoUrl(),
                    contentDescription = platform.name,
                    cornerRadius = PLATFORM_LOGO_CORNER_RADIUS,
                    modifier =
                        Modifier
                            .size(PLATFORM_LOGO_SIZE)
                            .clip(RoundedCornerShape(PLATFORM_LOGO_CORNER_RADIUS))
                            .clickable { onOpenLink(platform.url) },
                )
                Text(
                    text = stringResource(id = platform.type.labelRes()),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 4.dp),
                )
            }
        }
    }
}

@StringRes
private fun PlatformType.labelRes(): Int =
    when (this) {
        PlatformType.STREAM -> R.string.platforms_stream
        PlatformType.RENT -> R.string.platforms_rent
        PlatformType.BUY -> R.string.platforms_buy
    }

@Composable
private fun MovieCastRow(uiState: MovieUiState) {
    LazyRow(contentPadding = PaddingValues(horizontal = 10.dp)) {
        items(uiState.cast, key = { it.creditId }) { cast ->
            MoviePoster(
                posterPath = cast.profilePath,
                contentDescription = cast.name,
            )
        }
    }
}

private fun MovieEntity.metadataLine(): String =
    listOf(
        "★ ${voteAverage.format(VOTE_AVERAGE_DECIMALS)}",
        releaseDate.take(RELEASE_YEAR_LENGTH),
        runtime.getRuntime(),
        genres.joinToString(", ") { it.name },
    ).filter { it.isNotBlank() }.joinToString(" • ")

@Composable
private fun MovieActionsRow(
    movie: MovieEntity,
    viewModel: MovieActionsViewModel = hiltViewModel(),
) {
    val favorites by viewModel.favorites.collectAsStateWithLifecycle()
    val isFavorite = movie.id in favorites
    val context = LocalContext.current

    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.Center,
    ) {
        ActionItem(
            icon = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
            label = stringResource(id = R.string.my_list),
            onClick = { viewModel.toggleFavorite(movie.id) },
        )
        Spacer(modifier = Modifier.width(48.dp))
        ActionItem(
            icon = Icons.Filled.Share,
            label = stringResource(id = R.string.share),
            onClick = { shareMovie(context, movie) },
        )
    }
}

@Composable
private fun ActionItem(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier =
            Modifier
                .clip(RoundedCornerShape(10.dp))
                .clickable(onClick = onClick)
                .padding(horizontal = 12.dp, vertical = 6.dp),
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground,
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 4.dp),
        )
    }
}
