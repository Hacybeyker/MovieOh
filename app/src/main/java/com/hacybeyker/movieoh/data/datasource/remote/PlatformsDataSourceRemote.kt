package com.hacybeyker.movieoh.data.datasource.remote

import com.hacybeyker.movieoh.commons.base.NetworkResult
import com.hacybeyker.movieoh.data.api.PlatformsApi
import com.hacybeyker.movieoh.data.datasource.PlatformsDataSource
import com.hacybeyker.movieoh.data.model.remote.response.toEntities
import com.hacybeyker.movieoh.domain.entity.PlatformsEntity
import java.util.Locale
import javax.inject.Inject

private const val DEFAULT_COUNTRY = "US"

class PlatformsDataSourceRemote
    @Inject
    constructor(
        private val api: PlatformsApi,
    ) : PlatformsDataSource {
        override suspend fun getPlatforms(movieId: Int): NetworkResult<List<PlatformsEntity>> =
            try {
                val results = api.getWatchProviders(movieId).results.orEmpty()
                val country = results[Locale.getDefault().country] ?: results[DEFAULT_COUNTRY]
                NetworkResult.Success(country?.toEntities() ?: emptyList())
            } catch (e: Exception) {
                NetworkResult.Error(e.message)
            }
    }
