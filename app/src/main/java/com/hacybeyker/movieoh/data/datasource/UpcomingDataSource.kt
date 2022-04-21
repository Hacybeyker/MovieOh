package com.hacybeyker.movieoh.data.datasource

import com.hacybeyker.movieoh.domain.entity.MovieEntity

interface UpcomingDataSource {
    suspend fun fetchUpcoming(page: Int): List<MovieEntity>
}
