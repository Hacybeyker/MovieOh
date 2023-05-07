package com.hacybeyker.movieoh.data.api

import com.hacybeyker.movieoh.data.model.remote.response.ResultsPlatformResponseModel
import retrofit2.http.GET
import retrofit2.http.Query

fun interface PlatformsApi {

    @GET("search")
    suspend fun getPlatforms(
        @Query("title", encoded = true) title: String
    ): ResultsPlatformResponseModel
}
