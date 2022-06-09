package com.hacybeyker.movieoh.domain.entity

data class CrewEntity(
    val adult: Boolean,
    val gender: Int,
    val id: Int,
    val knownForDepartment: String,
    val name: String,
    val originalName: String,
    val popularity: Double,
    val profilePath: String,
    val creditId: String,
    val department: String,
    val job: String,
)
