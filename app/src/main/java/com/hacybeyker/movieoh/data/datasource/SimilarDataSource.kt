package com.hacybeyker.movieoh.data.datasource

import com.hacybeyker.movieoh.domain.entity.MovieEntity

fun interface SimilarDataSource {
    suspend fun fetchSimilar(movie: Int): List<MovieEntity>
}
