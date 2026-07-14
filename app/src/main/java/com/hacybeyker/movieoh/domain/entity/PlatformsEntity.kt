package com.hacybeyker.movieoh.domain.entity

enum class PlatformType {
    STREAM,
    RENT,
    BUY,
}

data class PlatformsEntity(
    val name: String,
    val logoPath: String,
    val url: String,
    val type: PlatformType,
)
