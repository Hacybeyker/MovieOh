package com.hacybeyker.movieoh.domain.usecase

import com.hacybeyker.movieoh.commons.base.NetworkResult
import com.hacybeyker.movieoh.domain.entity.PlatformsEntity
import com.hacybeyker.movieoh.domain.repository.PlatformsRepository
import javax.inject.Inject

class PlatformsUseCase
    @Inject
    constructor(
        private val repository: PlatformsRepository,
    ) {
        suspend fun getPlatforms(movieId: Int): NetworkResult<List<PlatformsEntity>> = repository.getPlatforms(movieId)
    }
