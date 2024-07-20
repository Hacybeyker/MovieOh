package com.hacybeyker.movieoh.data.repository

import com.hacybeyker.movieoh.data.datasource.MovieDataSource
import com.hacybeyker.movieoh.domain.entity.MovieEntity
import com.hacybeyker.movieoh.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryData
    @Inject
    constructor(
        private val dataSource: MovieDataSource,
    ) : MovieRepository {
        override suspend fun fetchMovie(movie: Int): MovieEntity {
            return dataSource.fetchMovie(movie)
        }
    }
