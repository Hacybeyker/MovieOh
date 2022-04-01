package com.hacybeyker.movieoh.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieEntity(
    val id: Int,
    val backdropPath: String,
    val posterPath: String,
    val originalTitle: String,
    val overview: String,
    val releaseDate: String,
    val title: String,
    val voteAverage: Double,
) : Parcelable
