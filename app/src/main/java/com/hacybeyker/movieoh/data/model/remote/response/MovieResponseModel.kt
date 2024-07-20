package com.hacybeyker.movieoh.data.model.remote.response

import androidx.annotation.VisibleForTesting
import com.google.gson.annotations.SerializedName
import com.hacybeyker.movieoh.domain.entity.MovieEntity
import com.hacybeyker.movieoh.domain.entity.StreamEntity
import com.hacybeyker.movieoh.utils.constans.Stream

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
) {
    @VisibleForTesting
    fun isAvailableStream(): Boolean {
        if (homepage == null) return false
        return homepage.contains(Stream.NETFLIX) ||
            homepage.contains(Stream.AMAZON) ||
            homepage.contains(Stream.HBO) ||
            homepage.contains(Stream.DISNEY)
    }

    fun assignedStream(): StreamEntity {
        var stream: StreamEntity = StreamEntity.NONE
        if (homepage != null && isAvailableStream()) {
            when {
                homepage.contains(Stream.NETFLIX) -> stream = StreamEntity.NETFLIX
                homepage.contains(Stream.AMAZON) -> stream = StreamEntity.AMAZON
                homepage.contains(Stream.HBO) -> stream = StreamEntity.HBO
                homepage.contains(Stream.DISNEY) -> stream = StreamEntity.DISNEY
            }
        }
        return stream
    }
}

fun MovieResponseModel.toEntity(): MovieEntity {
    return MovieEntity(
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
        stream = assignedStream(),
        genres = genres?.toListGenreEntity() ?: arrayListOf(),
    )
}
