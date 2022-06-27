package com.hacybeyker.movieoh.data.api

import com.hacybeyker.movieoh.data.model.remote.response.ResultResponseModel
import retrofit2.http.GET
import retrofit2.http.Query

interface DiscoverApi {
    @GET("discover/movie")
    suspend fun fetchDiscover(
        @Query("page") page: Int,
        @Query("with_genres") genre: Int
    ): ResultResponseModel
}
