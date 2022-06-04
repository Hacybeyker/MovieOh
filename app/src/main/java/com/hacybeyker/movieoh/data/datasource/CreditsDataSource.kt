package com.hacybeyker.movieoh.data.datasource

import com.hacybeyker.movieoh.domain.entity.CreditsEntity

interface CreditsDataSource {
    suspend fun fetchCredits(movie: Int): CreditsEntity
}
