package com.hacybeyker.movieoh.data.repository

import com.hacybeyker.movieoh.data.datasource.DiscoverDataSource
import com.hacybeyker.movieoh.domain.entity.MovieEntity
import com.hacybeyker.movieoh.domain.repository.DiscoverRepository
import javax.inject.Inject

class DiscoverRepositoryData
    @Inject
    constructor(
        private val dataSource: DiscoverDataSource,
    ) : DiscoverRepository {
        override suspend fun fetchDiscover(
            page: Int,
            genre: Int,
        ): List<MovieEntity> {
            return dataSource.fetchDiscover(page, genre)
        }
    }
