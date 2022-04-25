package com.hacybeyker.movieoh.domain.repository

import com.hacybeyker.movieoh.domain.entity.MovieEntity

interface UpcomingRepository {

    suspend fun fetchUpcoming(page: Int): List<MovieEntity>
}
