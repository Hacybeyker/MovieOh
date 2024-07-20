package com.hacybeyker.movieoh.domain.usecase

import com.hacybeyker.movieoh.domain.entity.MovieEntity
import com.hacybeyker.movieoh.domain.repository.MovieRepository
import javax.inject.Inject

class MovieUseCase
    @Inject
    constructor(
        private val movieRepository: MovieRepository,
    ) {
        suspend fun fetchMovie(movie: Int): MovieEntity {
            return movieRepository.fetchMovie(movie)
        }
    }
