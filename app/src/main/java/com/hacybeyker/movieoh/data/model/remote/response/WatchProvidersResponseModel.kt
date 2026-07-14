package com.hacybeyker.movieoh.data.model.remote.response

import com.google.gson.annotations.SerializedName
import com.hacybeyker.movieoh.domain.entity.PlatformsEntity

data class WatchProvidersResponseModel(
    @SerializedName("results")
    val results: Map<String, CountryWatchProvidersResponseModel>? = emptyMap(),
)

data class CountryWatchProvidersResponseModel(
    @SerializedName("link")
    val link: String? = "",
    @SerializedName("flatrate")
    val flatrate: List<WatchProviderResponseModel>? = emptyList(),
    @SerializedName("rent")
    val rent: List<WatchProviderResponseModel>? = emptyList(),
    @SerializedName("buy")
    val buy: List<WatchProviderResponseModel>? = emptyList(),
)

data class WatchProviderResponseModel(
    @SerializedName("provider_name")
    val providerName: String?,
    @SerializedName("logo_path")
    val logoPath: String?,
)

fun CountryWatchProvidersResponseModel.toEntities(): List<PlatformsEntity> =
    (flatrate.orEmpty() + rent.orEmpty() + buy.orEmpty())
        .distinctBy { it.providerName }
        .map { provider ->
            PlatformsEntity(
                name = provider.providerName.orEmpty(),
                logoPath = provider.logoPath.orEmpty(),
                url = link.orEmpty(),
            )
        }
