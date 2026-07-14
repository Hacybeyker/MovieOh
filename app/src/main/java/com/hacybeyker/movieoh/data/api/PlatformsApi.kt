package com.hacybeyker.movieoh.data.api

import com.hacybeyker.movieoh.data.model.remote.response.WatchProvidersResponseModel
import retrofit2.http.GET
import retrofit2.http.Path

fun interface PlatformsApi {
    @GET("movie/{movieId}/watch/providers")
    suspend fun getWatchProviders(
        @Path("movieId") movieId: Int,
    ): WatchProvidersResponseModel
}
