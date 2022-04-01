package com.hacybeyker.movieoh.data.model.remote.response

import com.google.gson.annotations.SerializedName
import com.hacybeyker.movieoh.domain.entity.MovieEntity

data class ResultResponseModel(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<MovieResponseModel>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)

fun ResultResponseModel.toMovieResponseModelList(): List<MovieEntity> {
    return results.map { it.toEntity() }
}
