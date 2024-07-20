package com.hacybeyker.movieoh.data.repository

import com.hacybeyker.movieoh.commons.base.NetworkResult
import com.hacybeyker.movieoh.data.datasource.PlatformsDataSource
import com.hacybeyker.movieoh.domain.entity.PlatformsEntity
import com.hacybeyker.movieoh.domain.repository.PlatformsRepository
import javax.inject.Inject

class PlatformsRepositoryData
    @Inject
    constructor(
        private val dataSource: PlatformsDataSource,
    ) : PlatformsRepository {
        override suspend fun getPlatforms(name: String): NetworkResult<List<PlatformsEntity>> {
            return dataSource.getPlatforms(name)
        }
    }
