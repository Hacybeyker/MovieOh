package com.hacybeyker.movieoh.data.datasource

import com.hacybeyker.movieoh.domain.entity.CreditsEntity

fun interface CreditsDataSource {
    suspend fun fetchCredits(movie: Int): CreditsEntity
}
