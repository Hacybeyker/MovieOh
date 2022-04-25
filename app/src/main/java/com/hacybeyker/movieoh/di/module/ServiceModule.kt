package com.hacybeyker.movieoh.di.module

import com.hacybeyker.movieoh.data.api.DiscoverApi
import com.hacybeyker.movieoh.data.api.TrendingMovieApi
import com.hacybeyker.movieoh.data.api.UpcomingApi
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

    @Singleton
    @Provides
    fun provideUpcomingServices(retrofit: Retrofit): UpcomingApi {
        return retrofit.create(UpcomingApi::class.java)
    }

    @Singleton
    @Provides
    fun provideDiscoverServices(retrofit: Retrofit): DiscoverApi {
        return retrofit.create(DiscoverApi::class.java)
    }
}
