package com.hacybeyker.movieoh.data.datasource.remote

import com.hacybeyker.movieoh.data.api.MovieApi
import com.hacybeyker.movieoh.data.datasource.MovieDataSource
import com.hacybeyker.movieoh.data.model.remote.response.toEntity
import com.hacybeyker.movieoh.domain.entity.MovieEntity
import javax.inject.Inject

class MovieDataSourceRemote @Inject constructor(
    private val api: MovieApi
) : MovieDataSource {

    override suspend fun fetchMovie(movie: Int): MovieEntity {
        return api.fetchMovie(movie).toEntity()
    }
}
