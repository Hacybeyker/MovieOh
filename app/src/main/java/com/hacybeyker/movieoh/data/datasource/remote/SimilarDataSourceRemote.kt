package com.hacybeyker.movieoh.data.datasource.remote

import com.hacybeyker.movieoh.data.api.SimilarApi
import com.hacybeyker.movieoh.data.datasource.SimilarDataSource
import com.hacybeyker.movieoh.data.model.remote.response.toMovieResponseModelList
import com.hacybeyker.movieoh.domain.entity.MovieEntity
import javax.inject.Inject

class SimilarDataSourceRemote @Inject constructor(
    private val api: SimilarApi
) : SimilarDataSource {

    override suspend fun fetchSimilar(movie: Int): List<MovieEntity> {
        return api.fetchSimilar(movie).toMovieResponseModelList()
    }
}
