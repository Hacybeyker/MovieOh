package com.hacybeyker.movieoh.ui.movieactions

import android.content.Context
import android.content.Intent
import com.hacybeyker.movieoh.domain.entity.MovieEntity

private const val TMDB_MOVIE_URL = "https://www.themoviedb.org/movie/"

internal fun shareMovie(
    context: Context,
    movie: MovieEntity,
) {
    val shareText = "${movie.title}\n$TMDB_MOVIE_URL${movie.id}"
    val intent =
        Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, shareText)
        }
    context.startActivity(Intent.createChooser(intent, null))
}
