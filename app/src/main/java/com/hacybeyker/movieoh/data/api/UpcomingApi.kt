package com.hacybeyker.movieoh.data.api

import com.hacybeyker.movieoh.data.model.remote.response.ResultResponseModel
import retrofit2.http.GET
import retrofit2.http.Query

interface UpcomingApi {
    @GET("movie/upcoming")
    suspend fun fetchUpcoming(@Query("page") page: Int): ResultResponseModel
}
