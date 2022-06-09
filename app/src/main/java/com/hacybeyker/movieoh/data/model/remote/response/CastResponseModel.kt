package com.hacybeyker.movieoh.data.model.remote.response

import com.google.gson.annotations.SerializedName
import com.hacybeyker.movieoh.domain.entity.CastEntity

data class CastResponseModel(
    @SerializedName("adult")
    val adult: Boolean?,
    @SerializedName("gender")
    val gender: Int?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("known_for_department")
    val knownForDepartment: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("original_name")
    val originalName: String?,
    @SerializedName("popularity")
    val popularity: Double?,
    @SerializedName("profile_path")
    val profilePath: String?,
    @SerializedName("cast_id")
    val castId: String?,
    @SerializedName("character")
    val character: String?,
    @SerializedName("credit_id")
    val creditId: String?,
    @SerializedName("order")
    val order: Int?,
)

fun List<CastResponseModel>.toListCastEntity(): List<CastEntity> {
    return this.map { it.toCastEntity() }.filter { it.profilePath != "" }
}

fun CastResponseModel.toCastEntity(): CastEntity {
    return CastEntity(
        adult = adult ?: false,
        gender = gender ?: -1,
        id = id ?: -1,
        knownForDepartment = knownForDepartment ?: "",
        name = name ?: "",
        originalName = originalName ?: "",
        popularity = popularity ?: 0.0,
        profilePath = profilePath ?: "",
        castId = castId ?: "",
        character = character ?: "",
        creditId = creditId ?: "",
        order = order ?: -1,
    )
}
