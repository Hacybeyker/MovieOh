package com.hacybeyker.movieoh.di.module.business

import com.hacybeyker.movieoh.data.datasource.DiscoverDataSource
import com.hacybeyker.movieoh.data.datasource.remote.DiscoverDataSourceRemote
import com.hacybeyker.movieoh.data.repository.DiscoverRepositoryData
import com.hacybeyker.movieoh.domain.repository.DiscoverRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class DiscoverModule {
    @Binds
    abstract fun bindsDiscoverDataSource(discoverDataSourceRemote: DiscoverDataSourceRemote): DiscoverDataSource

    @Binds
    abstract fun bindsDiscoverRepository(discoverRepositoryData: DiscoverRepositoryData): DiscoverRepository
}
