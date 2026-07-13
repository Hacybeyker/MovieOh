package com.hacybeyker.movieoh

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.hacybeyker.movieoh.ui.navigation.MovieOhNavHost
import com.hacybeyker.uikit.theme.MovieOhTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            MovieOhTheme {
                MovieOhNavHost()
            }
        }
    }
}
