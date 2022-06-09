package com.hacybeyker.movieoh.data.datasource

import com.hacybeyker.movieoh.domain.entity.MovieEntity

interface MovieDataSource {
    suspend fun fetchMovie(movie: Int): MovieEntity
}
