package com.hacybeyker.movieoh.domain.usecase

import com.hacybeyker.movieoh.domain.entity.MovieEntity
import com.hacybeyker.movieoh.domain.repository.DiscoverRepository
import javax.inject.Inject

class DiscoverUseCase
    @Inject
    constructor(
        private val discoverRepository: DiscoverRepository,
    ) {
        suspend fun fetchDiscover(
            page: Int,
            genre: Int,
        ): List<MovieEntity> {
            return discoverRepository.fetchDiscover(page, genre)
        }
    }
