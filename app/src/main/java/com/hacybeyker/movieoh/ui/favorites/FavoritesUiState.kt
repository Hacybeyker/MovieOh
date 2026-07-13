package com.hacybeyker.movieoh.ui.favorites

import com.hacybeyker.movieoh.domain.entity.MovieEntity

data class FavoritesUiState(
    val isLoading: Boolean = true,
    val movies: List<MovieEntity> = emptyList(),
)
