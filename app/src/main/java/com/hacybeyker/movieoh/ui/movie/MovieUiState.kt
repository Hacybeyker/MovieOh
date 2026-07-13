package com.hacybeyker.movieoh.ui.movie

import com.hacybeyker.movieoh.domain.entity.CastEntity
import com.hacybeyker.movieoh.domain.entity.MovieEntity
import com.hacybeyker.movieoh.domain.entity.PlatformsEntity

data class MovieUiState(
    val isLoading: Boolean = true,
    val movie: MovieEntity? = null,
    val cast: List<CastEntity> = emptyList(),
    val similar: List<MovieEntity> = emptyList(),
    val platforms: List<PlatformsEntity> = emptyList(),
    val platformsErrorMessage: String? = null,
)
