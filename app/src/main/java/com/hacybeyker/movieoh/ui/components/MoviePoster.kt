package com.hacybeyker.movieoh.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.hacybeyker.movieoh.utils.extensions.toTmdbImageUrl
import com.hacybeyker.uikit.component.NetworkImage

val POSTER_WIDTH = 109.dp
val POSTER_HEIGHT = 164.dp
val POSTER_CORNER_RADIUS = 5.dp

@Composable
fun MoviePoster(
    posterPath: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
) {
    NetworkImage(
        url = posterPath.toTmdbImageUrl(),
        contentDescription = contentDescription,
        cornerRadius = POSTER_CORNER_RADIUS,
        modifier =
            modifier
                .padding(5.dp)
                .size(width = POSTER_WIDTH, height = POSTER_HEIGHT)
                .clip(RoundedCornerShape(POSTER_CORNER_RADIUS))
                .let { base -> onClick?.let { base.clickable(onClick = it) } ?: base },
    )
}
