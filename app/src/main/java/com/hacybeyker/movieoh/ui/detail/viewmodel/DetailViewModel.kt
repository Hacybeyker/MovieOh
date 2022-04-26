package com.hacybeyker.movieoh.ui.detail.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hacybeyker.movieoh.di.module.CoroutineModule.Companion.DISPATCHER_IO
import com.hacybeyker.movieoh.domain.entity.MovieEntity
import com.hacybeyker.movieoh.domain.usecase.SimilarUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val similarUseCase: SimilarUseCase,
    @Named(DISPATCHER_IO) private val dispatcherIO: CoroutineDispatcher
) : ViewModel() {

    private val similar: MutableLiveData<List<MovieEntity>> by lazy { MutableLiveData<List<MovieEntity>>() }
    val similarLiveData: LiveData<List<MovieEntity>> get() = similar

    fun fetchSimilar(movie: Int) {
        viewModelScope.launch(dispatcherIO) {
            try {
                val similar = similarUseCase.fetchSimilar(movie)
                this@DetailViewModel.similar.postValue(similar)
            } catch (e: Exception) {
                Log.d("TAG", "Here - fetchSimilar: ${e.message}")
            }
        }
    }
}
