package com.hacybeyker.movieoh.di.module.business

import com.hacybeyker.movieoh.data.datasource.TrendingDataSource
import com.hacybeyker.movieoh.data.datasource.remote.TrendingDataSourceRemote
import com.hacybeyker.movieoh.data.repository.TrendingRepositoryData
import com.hacybeyker.movieoh.domain.repository.TrendingRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class TrendingModule {
    @Binds
    abstract fun bindTrendingDataSourceRemote(trendingDataSourceRemote: TrendingDataSourceRemote): TrendingDataSource

    @Binds
    abstract fun bindTrendingRepositoryData(trendingRepositoryData: TrendingRepositoryData): TrendingRepository
}
