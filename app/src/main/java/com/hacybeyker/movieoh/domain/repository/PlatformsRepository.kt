package com.hacybeyker.movieoh.domain.repository

import com.hacybeyker.movieoh.commons.base.NetworkResult
import com.hacybeyker.movieoh.domain.entity.PlatformsEntity

fun interface PlatformsRepository {
    suspend fun getPlatforms(movieId: Int): NetworkResult<List<PlatformsEntity>>
}
