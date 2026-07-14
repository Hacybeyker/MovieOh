package com.hacybeyker.movieoh.ui.movie

import android.content.Intent
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AssistChip
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import com.hacybeyker.movieoh.ui.components.POSTER_CORNER_RADIUS
import com.hacybeyker.movieoh.ui.components.POSTER_HEIGHT
import com.hacybeyker.movieoh.ui.components.POSTER_WIDTH
import com.hacybeyker.movieoh.ui.components.ShimmerMovieScreen
import com.hacybeyker.movieoh.ui.movieactions.MovieActionsBottomSheet
import com.hacybeyker.movieoh.utils.extensions.format
import com.hacybeyker.movieoh.utils.extensions.getRuntime
import com.hacybeyker.movieoh.utils.extensions.toTmdbImageUrl
import com.hacybeyker.movieoh.utils.extensions.toTmdbLogoUrl
import com.hacybeyker.uikit.component.NetworkImage
import com.hacybeyker.uikit.component.SectionHeader
import com.hacybeyker.uikit.R as UiKitR

private const val OVERVIEW_MAX_LINES = 4
private const val VOTE_AVERAGE_DECIMALS = 1
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
                    MovieHeader(movie = movie)
                    MovieDetail(
                        uiState = uiState,
                        onMovieClick = onMovieClick,
                        onMovieLongClick = { selectedMovie = it },
                        onOpenLink = onOpenLink,
                    )
                }
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.padding(start = 15.dp, top = 30.dp),
                ) {
                    Icon(
                        painter = painterResource(id = UiKitR.drawable.icon_arrow_left),
                        contentDescription = stringResource(id = R.string.back),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
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
private fun MovieHeader(movie: MovieEntity) {
    NetworkImage(
        url = movie.backdropPath.toTmdbImageUrl(),
        contentDescription = movie.title,
        contentScale = ContentScale.Crop,
        modifier =
            Modifier
                .fillMaxWidth()
                .height(260.dp),
    )
}

@Composable
private fun MovieDetail(
    uiState: MovieUiState,
    onMovieClick: (MovieEntity) -> Unit,
    onMovieLongClick: (MovieEntity) -> Unit,
    onOpenLink: (String) -> Unit,
) {
    val movie = checkNotNull(uiState.movie)
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .offset(y = (-20).dp)
                .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                .background(MaterialTheme.colorScheme.background),
    ) {
        Row(modifier = Modifier.padding(start = 15.dp, top = 15.dp, end = 15.dp)) {
            NetworkImage(
                url = movie.posterPath.toTmdbImageUrl(),
                contentDescription = movie.title,
                cornerRadius = POSTER_CORNER_RADIUS,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.size(width = POSTER_WIDTH, height = POSTER_HEIGHT),
            )
            Column(modifier = Modifier.padding(start = 15.dp)) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                )
                Text(
                    text = movie.releaseDate,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(top = 10.dp),
                )
            }
        }

        MovieInfoRow(movie = movie)

        SectionHeader(title = stringResource(id = R.string.about_movie), showIcon = false)
        Text(
            text = movie.overview,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = OVERVIEW_MAX_LINES,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(horizontal = 15.dp),
        )

        ChipsRow(
            labels = movie.genres.map { it.name },
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

@Composable
private fun MovieInfoRow(movie: MovieEntity) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(top = 15.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        InfoItem(
            title = movie.voteAverage.format(VOTE_AVERAGE_DECIMALS),
            titleIcon = UiKitR.drawable.icon_star,
            description = stringResource(id = R.string.opinions, movie.voteCount),
            modifier = Modifier.weight(1f),
        )
        InfoDivider()
        InfoItem(
            title = movie.runtime.getRuntime(),
            description = stringResource(id = R.string.duration),
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
private fun InfoItem(
    title: String,
    description: String,
    modifier: Modifier = Modifier,
    titleIcon: Int? = null,
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground,
            )
            titleIcon?.let { icon ->
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier =
                        Modifier
                            .padding(start = 2.dp)
                            .size(10.dp),
                )
            }
        }
        Text(
            text = description,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 5.dp),
        )
    }
}

@Composable
private fun InfoDivider() {
    Spacer(
        modifier =
            Modifier
                .width(1.dp)
                .height(20.dp)
                .background(MaterialTheme.colorScheme.outline),
    )
}

@Composable
private fun ChipsRow(
    labels: List<String>,
    modifier: Modifier = Modifier,
    onClick: ((Int) -> Unit)? = null,
) {
    Row(modifier = modifier.horizontalScroll(rememberScrollState())) {
        labels.forEachIndexed { index, label ->
            AssistChip(
                onClick = { onClick?.invoke(index) },
                label = { Text(text = label, style = MaterialTheme.typography.bodySmall) },
                modifier = Modifier.padding(end = 5.dp),
            )
        }
    }
}
