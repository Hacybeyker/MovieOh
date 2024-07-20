package com.hacybeyker.movieoh.domain.usecase

import com.hacybeyker.movieoh.domain.entity.MovieEntity
import com.hacybeyker.movieoh.domain.repository.UpcomingRepository
import javax.inject.Inject

class UpcomingUseCase
    @Inject
    constructor(
        private val upcomingRepository: UpcomingRepository,
    ) {
        suspend fun fetchUpcoming(page: Int): List<MovieEntity> {
            return upcomingRepository.fetchUpcoming(page)
        }
    }
