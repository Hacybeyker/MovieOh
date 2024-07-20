package com.hacybeyker.movieoh.di.module

import com.hacybeyker.movieoh.data.api.CreditsApi
import com.hacybeyker.movieoh.data.api.DiscoverApi
import com.hacybeyker.movieoh.data.api.MovieApi
import com.hacybeyker.movieoh.data.api.PlatformsApi
import com.hacybeyker.movieoh.data.api.SimilarApi
import com.hacybeyker.movieoh.data.api.TrendingMovieApi
import com.hacybeyker.movieoh.data.api.UpcomingApi
import com.hacybeyker.movieoh.utils.constans.ConstantsDI.Named.IDENTIFIER_PLATFORMS
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
    ): TrendingMovieApi {
        return retrofit.create(TrendingMovieApi::class.java)
    }

    @Singleton
    @Provides
    fun provideUpcomingServices(
        @Named(IDENTIFIER_TM_DB) retrofit: Retrofit,
    ): UpcomingApi {
        return retrofit.create(UpcomingApi::class.java)
    }

    @Singleton
    @Provides
    fun provideDiscoverServices(
        @Named(IDENTIFIER_TM_DB) retrofit: Retrofit,
    ): DiscoverApi {
        return retrofit.create(DiscoverApi::class.java)
    }

    @Singleton
    @Provides
    fun provideSimilarService(
        @Named(IDENTIFIER_TM_DB) retrofit: Retrofit,
    ): SimilarApi {
        return retrofit.create(SimilarApi::class.java)
    }

    @Singleton
    @Provides
    fun provideMovieService(
        @Named(IDENTIFIER_TM_DB) retrofit: Retrofit,
    ): MovieApi {
        return retrofit.create(MovieApi::class.java)
    }

    @Singleton
    @Provides
    fun provideCreditsApi(
        @Named(IDENTIFIER_TM_DB) retrofit: Retrofit,
    ): CreditsApi {
        return retrofit.create(CreditsApi::class.java)
    }

    @Singleton
    @Provides
    fun providePlatformsApi(
        @Named(IDENTIFIER_PLATFORMS) retrofit: Retrofit,
    ): PlatformsApi {
        return retrofit.create(PlatformsApi::class.java)
    }
}
