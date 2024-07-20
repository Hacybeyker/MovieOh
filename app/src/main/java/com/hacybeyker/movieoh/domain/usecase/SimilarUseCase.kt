package com.hacybeyker.movieoh.domain.usecase

import com.hacybeyker.movieoh.domain.entity.MovieEntity
import com.hacybeyker.movieoh.domain.repository.SimilarRepository
import javax.inject.Inject

class SimilarUseCase
    @Inject
    constructor(
        private val similarRepository: SimilarRepository,
    ) {
        suspend fun fetchSimilar(movie: Int): List<MovieEntity> {
            return similarRepository.fetchSimilar(movie)
        }
    }
