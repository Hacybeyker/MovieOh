package com.hacybeyker.movieoh.data.api

import com.hacybeyker.movieoh.data.model.remote.response.ResultResponseModel
import retrofit2.http.GET
import retrofit2.http.Path

fun interface SimilarApi {
    @GET("movie/{movie}/similar")
    suspend fun fetchSimilar(
        @Path(value = "movie") movie: Int,
    ): ResultResponseModel
}
