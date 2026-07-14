package com.hacybeyker.movieoh.data.model.remote.response

import com.google.gson.annotations.SerializedName
import com.hacybeyker.movieoh.domain.entity.MovieEntity

data class MovieResponseModel(
    @SerializedName("adult")
    val adult: Boolean?,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("genre_ids")
    val genreIds: List<Int>?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("media_type")
    val mediaType: String?,
    @SerializedName("original_language")
    val originalLanguage: String?,
    @SerializedName("original_title")
    val originalTitle: String?,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("popularity")
    val popularity: Double?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("video")
    val video: Boolean?,
    @SerializedName("vote_average")
    val voteAverage: Double?,
    @SerializedName("vote_count")
    val voteCount: Int?,
    @SerializedName("runtime")
    val runtime: Int?,
    @SerializedName("homepage")
    val homepage: String?,
    @SerializedName("genres")
    val genres: List<GenreResponseModel>?,
)

fun MovieResponseModel.toEntity(): MovieEntity =
    MovieEntity(
        id = id ?: 0,
        backdropPath = backdropPath ?: "",
        posterPath = posterPath ?: "",
        originalTitle = originalTitle ?: "",
        overview = overview ?: "",
        releaseDate = releaseDate ?: "",
        title = title ?: "",
        voteAverage = voteAverage ?: 0.0,
        voteCount = voteCount ?: 0,
        runtime = runtime ?: 0,
        homepage = homepage ?: "",
        genres = genres?.toListGenreEntity() ?: arrayListOf(),
    )
