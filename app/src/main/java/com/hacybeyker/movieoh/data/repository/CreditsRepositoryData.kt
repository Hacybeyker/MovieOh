package com.hacybeyker.movieoh.data.repository

import com.hacybeyker.movieoh.data.datasource.CreditsDataSource
import com.hacybeyker.movieoh.domain.entity.CreditsEntity
import com.hacybeyker.movieoh.domain.repository.CreditsRepository
import javax.inject.Inject

class CreditsRepositoryData @Inject constructor(
    private val dataSource: CreditsDataSource
) : CreditsRepository {

    override suspend fun fetchCredits(movie: Int): CreditsEntity {
        return dataSource.fetchCredits(movie)
    }
}
