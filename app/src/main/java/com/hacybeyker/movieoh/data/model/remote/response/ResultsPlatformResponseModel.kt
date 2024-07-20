package com.hacybeyker.movieoh.data.model.remote.response

import com.google.gson.annotations.SerializedName
import com.hacybeyker.movieoh.domain.entity.PlatformsEntity

data class ResultsPlatformResponseModel(
    @SerializedName("results")
    val results: List<ResultPlatformResponseModel>? = emptyList(),
)

data class ResultPlatformResponseModel(
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("platforms")
    val platforms: List<PlatformsResponseModel>? = emptyList(),
)

data class PlatformsResponseModel(
    @SerializedName("name")
    val name: String?,
    @SerializedName("url")
    val url: String?,
)

fun PlatformsResponseModel.toEntity(): PlatformsEntity {
    return PlatformsEntity(
        name = name ?: "",
        url = url ?: "",
    )
}
