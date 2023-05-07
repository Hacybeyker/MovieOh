package com.hacybeyker.movieoh.domain.repository

import com.hacybeyker.movieoh.domain.entity.MovieEntity

fun interface MovieRepository {
    suspend fun fetchMovie(movie: Int): MovieEntity
}
