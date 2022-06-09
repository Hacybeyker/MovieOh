package com.hacybeyker.movieoh.domain.repository

import com.hacybeyker.movieoh.domain.entity.MovieEntity

interface MovieRepository {
    suspend fun fetchMovie(movie: Int): MovieEntity
}
