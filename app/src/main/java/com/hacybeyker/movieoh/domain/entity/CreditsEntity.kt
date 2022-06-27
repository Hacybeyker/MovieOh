package com.hacybeyker.movieoh.domain.entity

data class CreditsEntity(
    val cast: List<CastEntity>,
    val crew: List<CrewEntity>
)
