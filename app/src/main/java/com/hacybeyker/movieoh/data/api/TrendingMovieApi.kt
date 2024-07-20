package com.hacybeyker.movieoh.data.api

import com.hacybeyker.movieoh.data.model.remote.response.ResultResponseModel
import retrofit2.http.GET
import retrofit2.http.Query

fun interface TrendingMovieApi {
    @GET("trending/movie/week")
    suspend fun fetchTrendingMovie(
        @Query("page") page: Int,
    ): ResultResponseModel
}
