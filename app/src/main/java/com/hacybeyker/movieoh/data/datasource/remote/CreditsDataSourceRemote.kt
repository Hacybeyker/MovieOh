package com.hacybeyker.movieoh.data.datasource.remote

import com.hacybeyker.movieoh.data.api.CreditsApi
import com.hacybeyker.movieoh.data.datasource.CreditsDataSource
import com.hacybeyker.movieoh.data.model.remote.response.toCreditsEntity
import com.hacybeyker.movieoh.domain.entity.CreditsEntity
import javax.inject.Inject

class CreditsDataSourceRemote
    @Inject
    constructor(
        private val api: CreditsApi,
    ) : CreditsDataSource {
        override suspend fun fetchCredits(movie: Int): CreditsEntity {
            return api.fetchCredits(movie).toCreditsEntity()
        }
    }
