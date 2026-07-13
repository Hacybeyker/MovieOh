package com.hacybeyker.movieoh.di.module.business

import com.hacybeyker.movieoh.data.repository.ThemeRepositoryData
import com.hacybeyker.movieoh.domain.repository.ThemeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class ThemeModule {
    @Binds
    abstract fun bindThemeRepositoryData(themeRepositoryData: ThemeRepositoryData): ThemeRepository
}
