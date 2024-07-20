package com.hacybeyker.movieoh.data.datasource.remote

import com.hacybeyker.movieoh.data.api.UpcomingApi
import com.hacybeyker.movieoh.data.datasource.UpcomingDataSource
import com.hacybeyker.movieoh.data.model.remote.response.toMovieResponseModelList
import com.hacybeyker.movieoh.domain.entity.MovieEntity
import javax.inject.Inject

class UpcomingDataSourceRemote
    @Inject
    constructor(
        private val api: UpcomingApi,
    ) : UpcomingDataSource {
        override suspend fun fetchUpcoming(page: Int): List<MovieEntity> {
            return api.fetchUpcoming(page).toMovieResponseModelList()
        }
    }
