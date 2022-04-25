package com.hacybeyker.movieoh.data.repository

import com.hacybeyker.movieoh.data.datasource.UpcomingDataSource
import com.hacybeyker.movieoh.domain.entity.MovieEntity
import com.hacybeyker.movieoh.domain.repository.UpcomingRepository
import javax.inject.Inject

class UpcomingRepositoryData @Inject constructor(
    private val dataSource: UpcomingDataSource
) : UpcomingRepository {

    override suspend fun fetchUpcoming(page: Int): List<MovieEntity> {
        return dataSource.fetchUpcoming(page)
    }
}
