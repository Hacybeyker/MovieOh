package com.hacybeyker.movieoh.utils

import com.hacybeyker.movieoh.data.model.remote.response.CreditsResponseModel
import com.hacybeyker.movieoh.data.model.remote.response.MovieResponseModel
import com.hacybeyker.movieoh.data.model.remote.response.ResultResponseModel
import com.hacybeyker.movieoh.data.model.remote.response.WatchProvidersResponseModel
import com.hacybeyker.movieoh.data.model.remote.response.toCreditsEntity
import com.hacybeyker.movieoh.data.model.remote.response.toEntities
import com.hacybeyker.movieoh.data.model.remote.response.toEntity
import com.hacybeyker.movieoh.data.model.remote.response.toMovieResponseModelList
import com.hacybeyker.movieoh.domain.entity.CreditsEntity
import com.hacybeyker.movieoh.domain.entity.MovieEntity
import com.hacybeyker.movieoh.domain.entity.PlatformsEntity

val mockMovieEntity: MovieEntity by lazy {
    JSONFileLoader()
        .loadJsonString<MovieResponseModel>("response_model_movie.json")
        .toEntity()
}

val mockCreditEntity: CreditsEntity by lazy {
    JSONFileLoader()
        .loadJsonString<CreditsResponseModel>("response_model_credits.json")
        .toCreditsEntity()
}

val mockSimilarMovies: List<MovieEntity> by lazy {
    JSONFileLoader()
        .loadJsonString<ResultResponseModel>("response_model_similar.json")
        .toMovieResponseModelList()
}

val mockPlatformsModel: WatchProvidersResponseModel by lazy {
    JSONFileLoader().loadJsonString<WatchProvidersResponseModel>("response_model_platforms.json")
}

val mockPlatformsEntity: List<PlatformsEntity> by lazy {
    mockPlatformsModel.results
        ?.get("US")
        ?.toEntities() ?: emptyList()
}
