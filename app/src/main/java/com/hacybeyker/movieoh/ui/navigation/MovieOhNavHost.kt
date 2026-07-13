package com.hacybeyker.movieoh.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.hacybeyker.movieoh.ui.favorites.FavoritesScreen
import com.hacybeyker.movieoh.ui.home.HomeScreen
import com.hacybeyker.movieoh.ui.movie.MovieScreen
import com.hacybeyker.movieoh.ui.settings.SettingsScreen
import com.hacybeyker.movieoh.ui.splash.SplashScreen

private const val FADE_DURATION_MILLIS = 300
private const val SLIDE_DURATION_MILLIS = 350

@Composable
fun MovieOhNavHost() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val showBottomBar = navBackStackEntry?.destination?.route in MovieOhDestinations.TAB_ROUTES

    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            if (showBottomBar) {
                MovieOhBottomBar(navController = navController)
            }
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = MovieOhDestinations.SPLASH,
            modifier = Modifier.padding(innerPadding),
            enterTransition = { fadeIn(animationSpec = tween(FADE_DURATION_MILLIS)) },
            exitTransition = { fadeOut(animationSpec = tween(FADE_DURATION_MILLIS)) },
            popEnterTransition = { fadeIn(animationSpec = tween(FADE_DURATION_MILLIS)) },
            popExitTransition = { fadeOut(animationSpec = tween(FADE_DURATION_MILLIS)) },
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
            composable(MovieOhDestinations.FAVORITES) {
                FavoritesScreen(
                    onMovieClick = { movie ->
                        navController.navigate(MovieOhDestinations.movie(movie.id))
                    },
                )
            }
            composable(MovieOhDestinations.SETTINGS) {
                SettingsScreen()
            }
            composable(
                route = MovieOhDestinations.MOVIE,
                arguments =
                    listOf(
                        navArgument(MovieOhDestinations.MOVIE_ID_ARG) { type = NavType.IntType },
                    ),
                enterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(SLIDE_DURATION_MILLIS),
                    ) + fadeIn(animationSpec = tween(SLIDE_DURATION_MILLIS))
                },
                popExitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(SLIDE_DURATION_MILLIS),
                    ) + fadeOut(animationSpec = tween(SLIDE_DURATION_MILLIS))
                },
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
}
