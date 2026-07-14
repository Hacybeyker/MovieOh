package com.hacybeyker.movieoh.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.hacybeyker.movieoh.R
import com.hacybeyker.uikit.theme.CinematicMagenta
import com.hacybeyker.uikit.theme.CinematicPurple

private data class BottomBarTab(
    val route: String,
    val labelRes: Int,
    val icon: ImageVector,
)

private val TABS =
    listOf(
        BottomBarTab(MovieOhDestinations.HOME, R.string.nav_home, Icons.Default.Home),
        BottomBarTab(MovieOhDestinations.FAVORITES, R.string.nav_favorites, Icons.Default.Favorite),
        BottomBarTab(MovieOhDestinations.SETTINGS, R.string.nav_settings, Icons.Default.Settings),
    )

@Composable
fun MovieOhBottomBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        TABS.forEach { tab ->
            NavigationBarItem(
                selected = currentRoute == tab.route,
                onClick = {
                    if (currentRoute != tab.route) {
                        navController.navigate(tab.route) {
                            popUpTo(MovieOhDestinations.HOME) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    Icon(
                        imageVector = tab.icon,
                        contentDescription = stringResource(id = tab.labelRes),
                    )
                },
                label = { Text(text = stringResource(id = tab.labelRes)) },
                colors =
                    NavigationBarItemDefaults.colors(
                        selectedIconColor = CinematicMagenta,
                        selectedTextColor = CinematicMagenta,
                        indicatorColor = CinematicPurple.copy(alpha = 0.2f),
                    ),
            )
        }
    }
}
