package com.hacybeyker.movieoh.ui.navigation

object MovieOhDestinations {
    const val SPLASH = "splash"
    const val HOME = "home"
    const val MOVIE = "movie/{movieId}"
    const val MOVIE_ID_ARG = "movieId"

    fun movie(movieId: Int): String = "movie/$movieId"
}
