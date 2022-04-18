package com.hacybeyker.movieoh.di.module

import com.hacybeyker.movieoh.data.api.TrendingMovieApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {

    @Singleton
    @Provides
    fun provideTrendingMovieServices(retrofit: Retrofit): TrendingMovieApi {
        return retrofit.create(TrendingMovieApi::class.java)
    }
}
