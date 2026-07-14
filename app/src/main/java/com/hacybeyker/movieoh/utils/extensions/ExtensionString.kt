package com.hacybeyker.movieoh.utils.extensions

private const val TMDB_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w1280"
private const val TMDB_LOGO_BASE_URL = "https://image.tmdb.org/t/p/w92"

fun String.toTmdbImageUrl(): String = if (isEmpty()) "" else "$TMDB_IMAGE_BASE_URL$this"

fun String.toTmdbLogoUrl(): String = if (isEmpty()) "" else "$TMDB_LOGO_BASE_URL$this"
