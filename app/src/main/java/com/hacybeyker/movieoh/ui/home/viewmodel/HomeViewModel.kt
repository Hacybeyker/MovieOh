package com.hacybeyker.movieoh.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hacybeyker.movieoh.di.module.CoroutineModule.Companion.DISPATCHER_IO
import com.hacybeyker.movieoh.domain.entity.MovieEntity
import com.hacybeyker.movieoh.domain.usecase.TrendingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val trendingUseCase: TrendingUseCase,
    @Named(DISPATCHER_IO) private val dispatcherIO: CoroutineDispatcher
) : ViewModel() {

    private val trending: MutableLiveData<List<MovieEntity>> by lazy { MutableLiveData<List<MovieEntity>>() }
    val trendingLiveData: LiveData<List<MovieEntity>> get() = trending

    private val loading: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    val loadingLiveData: LiveData<Boolean> get() = loading

    fun fetchTrendingMovie() {
        viewModelScope.launch(dispatcherIO) {
            try {
                loading.postValue(false)
                val response = trendingUseCase.fetchTrendingMovie(1)
                trending.postValue(response)
                loading.postValue(true)
            } catch (e: Exception) {
                trending.postValue(arrayListOf())
                loading.postValue(true)
            }
        }
    }
}
