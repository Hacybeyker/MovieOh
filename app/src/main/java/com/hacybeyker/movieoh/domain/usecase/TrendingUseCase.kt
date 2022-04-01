package com.hacybeyker.movieoh.domain.usecase

import com.hacybeyker.movieoh.domain.entity.MovieEntity
import com.hacybeyker.movieoh.domain.repository.TrendingRepository
import javax.inject.Inject

class TrendingUseCase @Inject constructor(private val repository: TrendingRepository) {

    suspend fun fetchTrendingMovie(page: Int): List<MovieEntity> {
        return repository.fetchTrendingMovie(page)
    }
}
