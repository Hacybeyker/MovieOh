package com.hacybeyker.movieoh.domain.usecase

import com.hacybeyker.movieoh.domain.entity.CreditsEntity
import com.hacybeyker.movieoh.domain.repository.CreditsRepository
import javax.inject.Inject

class CreditsUseCase
    @Inject
    constructor(
        private val repository: CreditsRepository,
    ) {
        suspend fun fetchCredits(movie: Int): CreditsEntity {
            return repository.fetchCredits(movie)
        }
    }
