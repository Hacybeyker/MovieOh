package com.hacybeyker.movieoh.data.datasource

import com.hacybeyker.movieoh.domain.entity.MovieEntity

fun interface TrendingDataSource {
    suspend fun fetchTrendingMovie(page: Int): List<MovieEntity>
}
