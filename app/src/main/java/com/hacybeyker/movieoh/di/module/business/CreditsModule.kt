package com.hacybeyker.movieoh.di.module.business

import com.hacybeyker.movieoh.data.datasource.CreditsDataSource
import com.hacybeyker.movieoh.data.datasource.remote.CreditsDataSourceRemote
import com.hacybeyker.movieoh.data.repository.CreditsRepositoryData
import com.hacybeyker.movieoh.domain.repository.CreditsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class CreditsModule {

    @Binds
    abstract fun bindCreditsDataSource(creditsDataSourceRemote: CreditsDataSourceRemote): CreditsDataSource

    @Binds
    abstract fun bindCreditsRepository(creditsRepositoryData: CreditsRepositoryData): CreditsRepository
}
