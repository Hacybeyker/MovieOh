package com.hacybeyker.movieoh.data.datasource

import com.hacybeyker.movieoh.commons.base.NetworkResult
import com.hacybeyker.movieoh.domain.entity.PlatformsEntity

fun interface PlatformsDataSource {

    suspend fun getPlatforms(name: String): NetworkResult<List<PlatformsEntity>>
}
