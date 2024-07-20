package com.hacybeyker.movieoh.data.model.remote.response

import com.google.gson.annotations.SerializedName
import com.hacybeyker.movieoh.domain.entity.GenreEntity

data class GenreResponseModel(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
)

fun List<GenreResponseModel>.toListGenreEntity(): List<GenreEntity> {
    return this.map { it.toGenreEntity() }
}

fun GenreResponseModel.toGenreEntity(): GenreEntity {
    return GenreEntity(
        id = id ?: -1,
        name = name ?: "",
    )
}
