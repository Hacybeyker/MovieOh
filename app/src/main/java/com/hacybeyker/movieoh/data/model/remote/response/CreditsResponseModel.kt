package com.hacybeyker.movieoh.data.model.remote.response

import com.google.gson.annotations.SerializedName
import com.hacybeyker.movieoh.domain.entity.CreditsEntity

data class CreditsResponseModel(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("cast")
    val cast: List<CastResponseModel>?,
    @SerializedName("crew")
    val crew: List<CrewResponseModel>?,
)

fun CreditsResponseModel.toCreditsEntity(): CreditsEntity {
    return CreditsEntity(
        cast = cast?.toListCastEntity() ?: emptyList(),
        crew = crew?.toListCrewEntity() ?: emptyList(),
    )
}
