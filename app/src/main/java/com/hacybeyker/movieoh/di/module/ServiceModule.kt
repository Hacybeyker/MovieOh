package com.hacybeyker.movieoh.di.module

import com.hacybeyker.movieoh.data.api.CreditsApi
import com.hacybeyker.movieoh.data.api.DiscoverApi
import com.hacybeyker.movieoh.data.api.MovieApi
import com.hacybeyker.movieoh.data.api.SimilarApi
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

    @Singleton
    @Provides
    fun provideSimilarService(retrofit: Retrofit): SimilarApi {
        return retrofit.create(SimilarApi::class.java)
    }

    @Singleton
    @Provides
    fun provideMovieService(retrofit: Retrofit): MovieApi {
        return retrofit.create(MovieApi::class.java)
    }

    @Singleton
    @Provides
    fun provideCreditsApi(retrofit: Retrofit): CreditsApi {
        return retrofit.create(CreditsApi::class.java)
    }
}
