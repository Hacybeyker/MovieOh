package com.hacybeyker.movieoh.di.module

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.hacybeyker.movieoh.BuildConfig
import com.hacybeyker.movieoh.commons.exception.ApiException
import com.hacybeyker.movieoh.commons.exception.NoInternetConnectionException
import com.hacybeyker.movieoh.commons.network.NetworkStatus
import com.hacybeyker.movieoh.utils.constans.ConstantsDI
import com.hacybeyker.movieoh.utils.constans.ConstantsDI.Named.BASE_URL_PLATFORMS
import com.hacybeyker.movieoh.utils.constans.ConstantsDI.Named.IDENTIFIER_PLATFORMS
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.mirrajabi.okhttpjsonmock.OkHttpMockInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkPlatformsModule {

    @Singleton
    @Provides
    @Named(BASE_URL_PLATFORMS)
    fun provideBaseUrlPlatforms(): String {
        return BuildConfig.BASE_URL_PLATFORMS
    }

    @Singleton
    @Named(IDENTIFIER_PLATFORMS)
    @Provides
    fun providerOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        okHttpMockInterceptor: OkHttpMockInterceptor,
        chuckerInterceptor: ChuckerInterceptor,
        apiInterceptor: ApiInterceptorPlatforms
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(ConstantsDI.Parameters.TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(ConstantsDI.Parameters.TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(ConstantsDI.Parameters.TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(chuckerInterceptor)
            .addInterceptor(if (BuildConfig.IS_DEVELOPMENT) okHttpMockInterceptor else apiInterceptor)
            .build()
    }

    @Singleton
    @Named(IDENTIFIER_PLATFORMS)
    @Provides
    fun providerRetrofitPlatforms(
        @Named(BASE_URL_PLATFORMS) baseUrl: String,
        @Named(IDENTIFIER_PLATFORMS) client: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    class ApiInterceptorPlatforms @Inject constructor(
        private val networkUtils: NetworkStatus
    ) : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            if (!networkUtils.isOnline()) {
                throw NoInternetConnectionException()
            }
            var request = chain.request()
            val url = request.url.newBuilder().build()
            request = Request.Builder().url(url).build()
            val response = chain.proceed(request)
            if (!response.isSuccessful && response.code != ConstantsDI.Http.RESPONSE_OK) {
                throw ApiException()
            }
            return response
        }
    }
}
