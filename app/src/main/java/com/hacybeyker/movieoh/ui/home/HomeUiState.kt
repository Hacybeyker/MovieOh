package com.hacybeyker.movieoh.ui.home

import androidx.annotation.StringRes
import com.hacybeyker.movieoh.domain.entity.MovieEntity

data class HomeUiState(
    val isLoading: Boolean = true,
    val sections: List<HomeSection> = emptyList(),
    val isError: Boolean = false,
)

data class HomeSection(
    @param:StringRes val titleRes: Int,
    val movies: List<MovieEntity>,
)
