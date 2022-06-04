package com.hacybeyker.movieoh.di.module.business

import com.hacybeyker.movieoh.data.datasource.MovieDataSource
import com.hacybeyker.movieoh.data.datasource.remote.MovieDataSourceRemote
import com.hacybeyker.movieoh.data.repository.MovieRepositoryData
import com.hacybeyker.movieoh.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class MovieModule {

    @Binds
    abstract fun bindMovieDataSource(movieDataSourceRemote: MovieDataSourceRemote): MovieDataSource

    @Binds
    abstract fun bindMovieRepository(movieRepositoryData: MovieRepositoryData): MovieRepository
}
