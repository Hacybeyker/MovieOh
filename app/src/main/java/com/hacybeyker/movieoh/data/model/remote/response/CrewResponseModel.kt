package com.hacybeyker.movieoh.data.model.remote.response

import com.google.gson.annotations.SerializedName
import com.hacybeyker.movieoh.domain.entity.CrewEntity

data class CrewResponseModel(
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
    @SerializedName("credit_id")
    val creditId: String?,
    @SerializedName("department")
    val department: String?,
    @SerializedName("job")
    val job: String?,
)

fun List<CrewResponseModel>.toListCrewEntity(): List<CrewEntity> {
    return this.map { it.toCrewEntity() }
}

fun CrewResponseModel.toCrewEntity(): CrewEntity {
    return CrewEntity(
        adult = adult ?: false,
        gender = gender ?: -1,
        id = id ?: -1,
        knownForDepartment = knownForDepartment ?: "",
        name = name ?: "",
        originalName = originalName ?: "",
        popularity = popularity ?: 0.0,
        profilePath = profilePath ?: "",
        creditId = creditId ?: "",
        department = department ?: "",
        job = job ?: ""
    )
}
