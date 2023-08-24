package com.hacybeyker.movieoh.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
class CoroutineModule {

    companion object {
        const val DISPATCHER_MAIN = "MainDispatcher"
        const val DISPATCHER_DEFAULT = "DefaultDispatcher"
        const val DISPATCHER_IO = "IODispatcher"
        const val DISPATCHER_UNCONFINED = "UnconfinedDispatcher"
    }

    @Named(DISPATCHER_MAIN)
    @Provides
    fun provideMainDispatcher(): CoroutineDispatcher {
        return Dispatchers.Main
    }

    @Named(DISPATCHER_DEFAULT)
    @Provides
    fun provideDefaultDispatcher(): CoroutineDispatcher {
        return Dispatchers.Default
    }

    @Named(DISPATCHER_IO)
    @Provides
    fun provideIODispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    @Named(DISPATCHER_UNCONFINED)
    @Provides
    fun provideUnconfinedDispatcher(): CoroutineDispatcher {
        return Dispatchers.Unconfined
    }
}
