package com.hacybeyker.movieoh.ui.navigation

object MovieOhDestinations {
    const val SPLASH = "splash"
    const val HOME = "home"
    const val FAVORITES = "favorites"
    const val SETTINGS = "settings"
    const val MOVIE = "movie/{movieId}"
    const val MOVIE_ID_ARG = "movieId"

    val TAB_ROUTES = setOf(HOME, FAVORITES, SETTINGS)

    fun movie(movieId: Int): String = "movie/$movieId"
}
