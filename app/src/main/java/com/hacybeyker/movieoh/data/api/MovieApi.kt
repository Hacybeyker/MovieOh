package com.hacybeyker.movieoh.data.api

import com.hacybeyker.movieoh.data.model.remote.response.MovieResponseModel
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApi {
    @GET("movie/{movie}")
    suspend fun fetchMovie(@Path(value = "movie") movie: Int): MovieResponseModel
}
