package com.hacybeyker.movieoh.di.module.business

import com.hacybeyker.movieoh.data.datasource.SimilarDataSource
import com.hacybeyker.movieoh.data.datasource.remote.SimilarDataSourceRemote
import com.hacybeyker.movieoh.data.repository.SimilarRepositoryData
import com.hacybeyker.movieoh.domain.repository.SimilarRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class SimilarModule {
    @Binds
    abstract fun bindSimilarDataSource(similarDataSourceRemote: SimilarDataSourceRemote): SimilarDataSource

    @Binds
    abstract fun bindSimilarRepository(similarRepositoryData: SimilarRepositoryData): SimilarRepository
}
