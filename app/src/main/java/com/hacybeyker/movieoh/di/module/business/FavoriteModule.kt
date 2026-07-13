package com.hacybeyker.movieoh.di.module.business

import com.hacybeyker.movieoh.data.repository.FavoriteRepositoryData
import com.hacybeyker.movieoh.domain.repository.FavoriteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class FavoriteModule {
    @Binds
    abstract fun bindFavoriteRepositoryData(favoriteRepositoryData: FavoriteRepositoryData): FavoriteRepository
}
