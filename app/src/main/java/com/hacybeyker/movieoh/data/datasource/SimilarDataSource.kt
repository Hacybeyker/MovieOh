package com.hacybeyker.movieoh.data.datasource

import com.hacybeyker.movieoh.domain.entity.MovieEntity

interface SimilarDataSource {
    suspend fun fetchSimilar(movie: Int): List<MovieEntity>
}
