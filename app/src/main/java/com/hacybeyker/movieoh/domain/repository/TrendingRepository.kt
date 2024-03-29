package com.hacybeyker.movieoh.domain.repository

import com.hacybeyker.movieoh.domain.entity.MovieEntity

fun interface TrendingRepository {
    suspend fun fetchTrendingMovie(page: Int): List<MovieEntity>
}
