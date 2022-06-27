package com.hacybeyker.movieoh.domain.repository

import com.hacybeyker.movieoh.domain.entity.CreditsEntity

interface CreditsRepository {
    suspend fun fetchCredits(movie: Int): CreditsEntity
}
