package com.hacybeyker.movieoh.domain.repository

import com.hacybeyker.movieoh.domain.entity.MovieEntity

interface SimilarRepository {
    suspend fun fetchSimilar(movie: Int): List<MovieEntity>
}
