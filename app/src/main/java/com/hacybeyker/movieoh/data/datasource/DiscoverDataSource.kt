package com.hacybeyker.movieoh.data.datasource

import com.hacybeyker.movieoh.domain.entity.MovieEntity

fun interface DiscoverDataSource {
    suspend fun fetchDiscover(page: Int, genre: Int): List<MovieEntity>
}
