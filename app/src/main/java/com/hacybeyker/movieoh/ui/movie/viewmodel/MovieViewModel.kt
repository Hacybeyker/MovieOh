package com.hacybeyker.movieoh.ui.movie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hacybeyker.movieoh.commons.base.NetworkResult
import com.hacybeyker.movieoh.di.module.CoroutineModule.Companion.DISPATCHER_IO
import com.hacybeyker.movieoh.domain.entity.CreditsEntity
import com.hacybeyker.movieoh.domain.entity.MovieEntity
import com.hacybeyker.movieoh.domain.entity.PlatformsEntity
import com.hacybeyker.movieoh.domain.usecase.CreditsUseCase
import com.hacybeyker.movieoh.domain.usecase.MovieUseCase
import com.hacybeyker.movieoh.domain.usecase.PlatformsUseCase
import com.hacybeyker.movieoh.domain.usecase.SimilarUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import javax.inject.Named
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase,
    private val creditsUseCase: CreditsUseCase,
    private val similarUseCase: SimilarUseCase,
    private val platformsUseCase: PlatformsUseCase,
    @Named(DISPATCHER_IO)
    private val dispatcherIO: CoroutineDispatcher
) : ViewModel() {

    private val similar: MutableLiveData<List<MovieEntity>> by lazy { MutableLiveData<List<MovieEntity>>() }
    val similarLiveData: LiveData<List<MovieEntity>> get() = similar

    private val movie: MutableLiveData<MovieEntity> by lazy { MutableLiveData<MovieEntity>() }
    val movieLiveData: LiveData<MovieEntity> get() = movie

    private val credits: MutableLiveData<CreditsEntity> by lazy { MutableLiveData<CreditsEntity>() }
    val creditsLiveData: LiveData<CreditsEntity> get() = credits

    private val _platforms: MutableLiveData<NetworkResult<List<PlatformsEntity>>> by lazy { MutableLiveData<NetworkResult<List<PlatformsEntity>>>() }
    val platforms: LiveData<NetworkResult<List<PlatformsEntity>>> get() = _platforms

    private val loading: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    val loadingLiveData: LiveData<Boolean> get() = loading

    fun init(movieEntity: MovieEntity) {
        viewModelScope.launch(dispatcherIO) {
            try {
                loading.postValue(true)
                val similar = async { similarUseCase.fetchSimilar(movieEntity.id) }
                val movie = async { movieUseCase.fetchMovie(movieEntity.id) }
                val credits = async { creditsUseCase.fetchCredits(movieEntity.id) }
                val platforms = async { platformsUseCase.getPlatforms(movieEntity.title) }
                this@MovieViewModel.similar.postValue(similar.await())
                this@MovieViewModel.movie.postValue(movie.await())
                this@MovieViewModel.credits.postValue(credits.await())
                this@MovieViewModel._platforms.postValue(platforms.await())
                loading.postValue(false)
            } catch (e: Exception) {
                loading.postValue(false)
            }
        }
    }
}
