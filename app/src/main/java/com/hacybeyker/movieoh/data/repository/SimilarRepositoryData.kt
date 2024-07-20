package com.hacybeyker.movieoh.data.repository

import com.hacybeyker.movieoh.data.datasource.SimilarDataSource
import com.hacybeyker.movieoh.domain.entity.MovieEntity
import com.hacybeyker.movieoh.domain.repository.SimilarRepository
import javax.inject.Inject

class SimilarRepositoryData
    @Inject
    constructor(
        private val dataSource: SimilarDataSource,
    ) : SimilarRepository {
        override suspend fun fetchSimilar(movie: Int): List<MovieEntity> {
            return dataSource.fetchSimilar(movie)
        }
    }
