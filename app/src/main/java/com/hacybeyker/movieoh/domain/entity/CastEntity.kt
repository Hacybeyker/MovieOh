package com.hacybeyker.movieoh.domain.entity

data class CastEntity(
    val adult: Boolean,
    val gender: Int,
    val id: Int,
    val knownForDepartment: String,
    val name: String,
    val originalName: String,
    val popularity: Double,
    val profilePath: String,
    val castId: String,
    val character: String,
    val creditId: String,
    val order: Int,
)
