package com.hacybeyker.movieoh.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.hacybeyker.movieoh.ui.home.HomeScreen
import com.hacybeyker.movieoh.ui.movie.MovieScreen
import com.hacybeyker.movieoh.ui.splash.SplashScreen

@Composable
fun MovieOhNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = MovieOhDestinations.SPLASH,
    ) {
        composable(MovieOhDestinations.SPLASH) {
            SplashScreen(
                onFinished = {
                    navController.navigate(MovieOhDestinations.HOME) {
                        popUpTo(MovieOhDestinations.SPLASH) { inclusive = true }
                    }
                },
            )
        }
        composable(MovieOhDestinations.HOME) {
            HomeScreen(
                onMovieClick = { movie ->
                    navController.navigate(MovieOhDestinations.movie(movie.id))
                },
            )
        }
        composable(
            route = MovieOhDestinations.MOVIE,
            arguments =
                listOf(
                    navArgument(MovieOhDestinations.MOVIE_ID_ARG) { type = NavType.IntType },
                ),
        ) {
            MovieScreen(
                onMovieClick = { movie ->
                    navController.navigate(MovieOhDestinations.movie(movie.id))
                },
                onBackClick = { navController.popBackStack() },
            )
        }
    }
}
