package com.hacybeyker.movieoh.di.module

import com.hacybeyker.movieoh.data.datasource.UpcomingDataSource
import com.hacybeyker.movieoh.data.datasource.remote.UpcomingDataSourceRemote
import com.hacybeyker.movieoh.data.repository.UpcomingRepositoryData
import com.hacybeyker.movieoh.domain.repository.UpcomingRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class UpcomingModule {

    @Binds
    abstract fun bindUpcomingRepository(upcomingRepositoryData: UpcomingRepositoryData): UpcomingRepository

    @Binds
    abstract fun bindUpcomingDataSource(upcomingDataSourceRemote: UpcomingDataSourceRemote): UpcomingDataSource
}
