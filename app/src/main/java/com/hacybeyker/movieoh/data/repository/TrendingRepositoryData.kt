package com.hacybeyker.movieoh.data.repository

import com.hacybeyker.movieoh.data.datasource.TrendingDataSource
import com.hacybeyker.movieoh.domain.entity.MovieEntity
import com.hacybeyker.movieoh.domain.repository.TrendingRepository
import javax.inject.Inject

class TrendingRepositoryData
    @Inject
    constructor(
        private val dataSource: TrendingDataSource,
    ) : TrendingRepository {
        override suspend fun fetchTrendingMovie(page: Int): List<MovieEntity> {
            return dataSource.fetchTrendingMovie(page)
        }
    }
