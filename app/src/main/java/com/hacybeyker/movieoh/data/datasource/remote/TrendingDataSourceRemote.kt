package com.hacybeyker.movieoh.data.datasource.remote

import com.hacybeyker.movieoh.data.api.TrendingMovieApi
import com.hacybeyker.movieoh.data.datasource.TrendingDataSource
import com.hacybeyker.movieoh.data.model.remote.response.toMovieResponseModelList
import com.hacybeyker.movieoh.domain.entity.MovieEntity
import javax.inject.Inject

class TrendingDataSourceRemote @Inject constructor(
    private val api: TrendingMovieApi
) : TrendingDataSource {

    override suspend fun fetchTrendingMovie(page: Int): List<MovieEntity> {
        return api.fetchTrendingMovie(page).toMovieResponseModelList()
    }
}
