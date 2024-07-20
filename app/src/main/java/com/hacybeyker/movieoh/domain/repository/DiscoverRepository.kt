package com.hacybeyker.movieoh.domain.repository

import com.hacybeyker.movieoh.domain.entity.MovieEntity

fun interface DiscoverRepository {
    suspend fun fetchDiscover(
        page: Int,
        genre: Int,
    ): List<MovieEntity>
}
