package com.hacybeyker.movieoh.utils

import com.hacybeyker.movieoh.data.model.remote.response.CreditsResponseModel
import com.hacybeyker.movieoh.data.model.remote.response.MovieResponseModel
import com.hacybeyker.movieoh.data.model.remote.response.ResultPlatformResponseModel
import com.hacybeyker.movieoh.data.model.remote.response.ResultResponseModel
import com.hacybeyker.movieoh.data.model.remote.response.ResultsPlatformResponseModel
import com.hacybeyker.movieoh.data.model.remote.response.toCreditsEntity
import com.hacybeyker.movieoh.data.model.remote.response.toEntity
import com.hacybeyker.movieoh.data.model.remote.response.toMovieResponseModelList
import com.hacybeyker.movieoh.domain.entity.CreditsEntity
import com.hacybeyker.movieoh.domain.entity.MovieEntity
import com.hacybeyker.movieoh.domain.entity.PlatformsEntity

val mockMovieEntity: MovieEntity by lazy {
    JSONFileLoader().loadJsonString<MovieResponseModel>("response_model_movie.json")
        .toEntity()
}

val mockCreditEntity: CreditsEntity by lazy {
    JSONFileLoader().loadJsonString<CreditsResponseModel>("response_model_credits.json")
        .toCreditsEntity()
}

val mockSimilarMovies: List<MovieEntity> by lazy {
    JSONFileLoader().loadJsonString<ResultResponseModel>("response_model_similar.json")
        .toMovieResponseModelList()
}

val mockPlatformsEntity: List<PlatformsEntity> by lazy {
    JSONFileLoader().loadJsonString<ResultPlatformResponseModel>("response_model_platforms.json")
        .platforms?.map { it.toEntity() } ?: emptyList()
}

val mockPlatformsModel: ResultsPlatformResponseModel by lazy {
    JSONFileLoader().loadJsonString<ResultsPlatformResponseModel>("response_model_platforms.json")
}
