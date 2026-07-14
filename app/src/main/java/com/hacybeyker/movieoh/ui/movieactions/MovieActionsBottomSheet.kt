package com.hacybeyker.movieoh.ui.movieactions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hacybeyker.movieoh.R
import com.hacybeyker.movieoh.domain.entity.MovieEntity
import com.hacybeyker.movieoh.ui.components.POSTER_CORNER_RADIUS
import com.hacybeyker.movieoh.utils.extensions.toTmdbImageUrl
import com.hacybeyker.uikit.component.NetworkImage

private val POSTER_THUMB_WIDTH = 48.dp
private val POSTER_THUMB_HEIGHT = 72.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieActionsBottomSheet(
    movie: MovieEntity,
    onDismiss: () -> Unit,
    viewModel: MovieActionsViewModel = hiltViewModel(),
) {
    val favorites by viewModel.favorites.collectAsStateWithLifecycle()
    val isFavorite = movie.id in favorites
    val context = LocalContext.current

    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column {
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                NetworkImage(
                    url = movie.posterPath.toTmdbImageUrl(),
                    contentDescription = movie.title,
                    cornerRadius = POSTER_CORNER_RADIUS,
                    modifier = Modifier.size(width = POSTER_THUMB_WIDTH, height = POSTER_THUMB_HEIGHT),
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
            MovieActionRow(
                icon = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                label = stringResource(id = if (isFavorite) R.string.remove_favorite else R.string.add_favorite),
                onClick = { viewModel.toggleFavorite(movie.id) },
            )
            MovieActionRow(
                icon = Icons.Filled.Share,
                label = stringResource(id = R.string.share),
                onClick = {
                    shareMovie(context, movie)
                    onDismiss()
                },
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
private fun MovieActionRow(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .padding(horizontal = 20.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground,
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
        )
    }
}
