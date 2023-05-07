package com.hacybeyker.movieoh.di.module.business

import com.hacybeyker.movieoh.data.datasource.PlatformsDataSource
import com.hacybeyker.movieoh.data.datasource.remote.PlatformsDataSourceRemote
import com.hacybeyker.movieoh.data.repository.PlatformsRepositoryData
import com.hacybeyker.movieoh.domain.repository.PlatformsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class PlatformsModule {

    @Binds
    abstract fun bindPlatformsDataSource(platformsDataSourceRemote: PlatformsDataSourceRemote): PlatformsDataSource

    @Binds
    abstract fun bindPlatformsRepository(platformsRepositoryData: PlatformsRepositoryData): PlatformsRepository
}
