package com.hacybeyker.movieoh.data.datasource.remote

import com.hacybeyker.movieoh.data.api.DiscoverApi
import com.hacybeyker.movieoh.data.datasource.DiscoverDataSource
import com.hacybeyker.movieoh.data.model.remote.response.toMovieResponseModelList
import com.hacybeyker.movieoh.domain.entity.MovieEntity
import javax.inject.Inject

class DiscoverDataSourceRemote @Inject constructor(
    private val api: DiscoverApi
) : DiscoverDataSource {

    override suspend fun fetchDiscover(page: Int, genre: Int): List<MovieEntity> {
        return api.fetchDiscover(page, genre).toMovieResponseModelList()
    }
}
