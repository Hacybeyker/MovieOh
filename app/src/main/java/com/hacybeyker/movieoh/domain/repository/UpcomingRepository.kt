package com.hacybeyker.movieoh.domain.repository

import com.hacybeyker.movieoh.domain.entity.MovieEntity

fun interface UpcomingRepository {
    suspend fun fetchUpcoming(page: Int): List<MovieEntity>
}
