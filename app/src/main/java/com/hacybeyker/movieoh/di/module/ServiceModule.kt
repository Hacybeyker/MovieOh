package com.hacybeyker.movieoh.di.module

import com.hacybeyker.movieoh.data.api.CreditsApi
import com.hacybeyker.movieoh.data.api.DiscoverApi
import com.hacybeyker.movieoh.data.api.MovieApi
import com.hacybeyker.movieoh.data.api.PlatformsApi
import com.hacybeyker.movieoh.data.api.SimilarApi
import com.hacybeyker.movieoh.data.api.TrendingMovieApi
import com.hacybeyker.movieoh.data.api.UpcomingApi
import com.hacybeyker.movieoh.utils.constans.ConstantsDI.Named.IDENTIFIER_TM_DB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {
    @Singleton
    @Provides
    fun provideTrendingMovieServices(
        @Named(IDENTIFIER_TM_DB) retrofit: Retrofit,
    ): TrendingMovieApi = retrofit.create(TrendingMovieApi::class.java)

    @Singleton
    @Provides
    fun provideUpcomingServices(
        @Named(IDENTIFIER_TM_DB) retrofit: Retrofit,
    ): UpcomingApi = retrofit.create(UpcomingApi::class.java)

    @Singleton
    @Provides
    fun provideDiscoverServices(
        @Named(IDENTIFIER_TM_DB) retrofit: Retrofit,
    ): DiscoverApi = retrofit.create(DiscoverApi::class.java)

    @Singleton
    @Provides
    fun provideSimilarService(
        @Named(IDENTIFIER_TM_DB) retrofit: Retrofit,
    ): SimilarApi = retrofit.create(SimilarApi::class.java)

    @Singleton
    @Provides
    fun provideMovieService(
        @Named(IDENTIFIER_TM_DB) retrofit: Retrofit,
    ): MovieApi = retrofit.create(MovieApi::class.java)

    @Singleton
    @Provides
    fun provideCreditsApi(
        @Named(IDENTIFIER_TM_DB) retrofit: Retrofit,
    ): CreditsApi = retrofit.create(CreditsApi::class.java)

    @Singleton
    @Provides
    fun providePlatformsApi(
        @Named(IDENTIFIER_TM_DB) retrofit: Retrofit,
    ): PlatformsApi = retrofit.create(PlatformsApi::class.java)
}
