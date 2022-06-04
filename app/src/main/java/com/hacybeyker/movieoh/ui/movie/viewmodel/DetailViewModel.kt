package com.hacybeyker.movieoh.ui.movie.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hacybeyker.movieoh.di.module.CoroutineModule.Companion.DISPATCHER_IO
import com.hacybeyker.movieoh.domain.entity.CreditsEntity
import com.hacybeyker.movieoh.domain.entity.MovieEntity
import com.hacybeyker.movieoh.domain.usecase.CreditsUseCase
import com.hacybeyker.movieoh.domain.usecase.MovieUseCase
import com.hacybeyker.movieoh.domain.usecase.SimilarUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase,
    private val creditsUseCase: CreditsUseCase,
    private val similarUseCase: SimilarUseCase,
    @Named(DISPATCHER_IO) private val dispatcherIO: CoroutineDispatcher
) : ViewModel() {

    private val similar: MutableLiveData<List<MovieEntity>> by lazy { MutableLiveData<List<MovieEntity>>() }
    val similarLiveData: LiveData<List<MovieEntity>> get() = similar

    private val movie: MutableLiveData<MovieEntity> by lazy { MutableLiveData<MovieEntity>() }
    val movieLiveData: LiveData<MovieEntity> get() = movie

    private val credits: MutableLiveData<CreditsEntity> by lazy { MutableLiveData<CreditsEntity>() }
    val creditsLiveData: LiveData<CreditsEntity> get() = credits

    fun init(movieId: Int) {
        viewModelScope.launch(dispatcherIO) {
            try {
                val similar = async { similarUseCase.fetchSimilar(movieId) }
                val movie = async { movieUseCase.fetchMovie(movieId) }
                val credits = async { creditsUseCase.fetchCredits(movieId) }
                this@DetailViewModel.similar.postValue(similar.await())
                this@DetailViewModel.movie.postValue(movie.await())
                this@DetailViewModel.credits.postValue(credits.await())
            } catch (e: Exception) {
                Log.d("TAG", "Here - fetchSimilar: ${e.message}")
            }
        }
    }
}
