package com.hacybeyker.movieoh.data.api

import com.hacybeyker.movieoh.data.model.remote.response.CreditsResponseModel
import retrofit2.http.GET
import retrofit2.http.Path

interface CreditsApi {
    @GET("movie/{movie}/credits")
    suspend fun fetchCredits(@Path(value = "movie") movie: Int): CreditsResponseModel
}
