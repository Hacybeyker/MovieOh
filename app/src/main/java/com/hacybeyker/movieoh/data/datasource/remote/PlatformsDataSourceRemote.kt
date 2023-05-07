package com.hacybeyker.movieoh.data.datasource.remote

import com.hacybeyker.movieoh.commons.base.NetworkResult
import com.hacybeyker.movieoh.data.api.PlatformsApi
import com.hacybeyker.movieoh.data.datasource.PlatformsDataSource
import com.hacybeyker.movieoh.data.model.remote.response.toEntity
import com.hacybeyker.movieoh.domain.entity.PlatformsEntity
import javax.inject.Inject

class PlatformsDataSourceRemote @Inject constructor(
    private val api: PlatformsApi
) : PlatformsDataSource {

    override suspend fun getPlatforms(name: String): NetworkResult<List<PlatformsEntity>> {
        return try {
            val result =
                api.getPlatforms(name).results?.firstOrNull()?.platforms?.map { it.toEntity() }
                    ?: emptyList()
            return NetworkResult.Success(result)
        } catch (e: Exception) {
            NetworkResult.Error(e.message)
        }
    }
}
