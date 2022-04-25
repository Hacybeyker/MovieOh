package com.hacybeyker.movieoh.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hacybeyker.movieoh.di.module.CoroutineModule.Companion.DISPATCHER_IO
import com.hacybeyker.movieoh.domain.entity.MovieEntity
import com.hacybeyker.movieoh.domain.usecase.DiscoverUseCase
import com.hacybeyker.movieoh.domain.usecase.TrendingUseCase
import com.hacybeyker.movieoh.domain.usecase.UpcomingUseCase
import com.hacybeyker.movieoh.utils.constans.Genre.ACTION
import com.hacybeyker.movieoh.utils.constans.Genre.ADVENTURE
import com.hacybeyker.movieoh.utils.constans.Genre.ANIMATION
import com.hacybeyker.movieoh.utils.constans.Genre.COMEDY
import com.hacybeyker.movieoh.utils.constans.Genre.DRAMA
import com.hacybeyker.movieoh.utils.constans.Genre.PAGE_STAR
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val trendingUseCase: TrendingUseCase,
    private val upcomingUseCase: UpcomingUseCase,
    private val discoverUseCase: DiscoverUseCase,
    @Named(DISPATCHER_IO) private val dispatcherIO: CoroutineDispatcher
) : ViewModel() {

    private val trending: MutableLiveData<List<MovieEntity>> by lazy { MutableLiveData<List<MovieEntity>>() }
    val trendingLiveData: LiveData<List<MovieEntity>> get() = trending

    private val upcoming: MutableLiveData<List<MovieEntity>> by lazy { MutableLiveData<List<MovieEntity>>() }
    val upcomingLiveData: LiveData<List<MovieEntity>> get() = upcoming

    private val action: MutableLiveData<List<MovieEntity>> by lazy { MutableLiveData<List<MovieEntity>>() }
    val actionLiveData: LiveData<List<MovieEntity>> get() = action

    private val animation: MutableLiveData<List<MovieEntity>> by lazy { MutableLiveData<List<MovieEntity>>() }
    val animationLiveData: LiveData<List<MovieEntity>> get() = animation

    private val comedy: MutableLiveData<List<MovieEntity>> by lazy { MutableLiveData<List<MovieEntity>>() }
    val comedyLiveData: LiveData<List<MovieEntity>> get() = comedy

    private val drama: MutableLiveData<List<MovieEntity>> by lazy { MutableLiveData<List<MovieEntity>>() }
    val dramaLiveData: LiveData<List<MovieEntity>> get() = drama

    private val adventure: MutableLiveData<List<MovieEntity>> by lazy { MutableLiveData<List<MovieEntity>>() }
    val adventureLiveData: LiveData<List<MovieEntity>> get() = adventure

    private val loading: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    val loadingLiveData: LiveData<Boolean> get() = loading

    fun fetchTrendingMovie() {
        viewModelScope.launch(dispatcherIO) {
            try {
                val timeStart = System.currentTimeMillis()
                println("Here - TimeStart: $timeStart")
                loading.postValue(false)
                val trending = async { trendingUseCase.fetchTrendingMovie(PAGE_STAR) }
                val upcoming = async { upcomingUseCase.fetchUpcoming(PAGE_STAR) }
                val action = async { discoverUseCase.fetchDiscover(PAGE_STAR, ACTION) }
                val animation = async { discoverUseCase.fetchDiscover(PAGE_STAR, ANIMATION) }
                val comedy = async { discoverUseCase.fetchDiscover(PAGE_STAR, COMEDY) }
                val drama = async { discoverUseCase.fetchDiscover(PAGE_STAR, DRAMA) }
                val adventure = async { discoverUseCase.fetchDiscover(PAGE_STAR, ADVENTURE) }
                this@HomeViewModel.trending.postValue(trending.await())
                this@HomeViewModel.upcoming.postValue(upcoming.await())
                this@HomeViewModel.action.postValue(action.await())
                this@HomeViewModel.animation.postValue(animation.await())
                this@HomeViewModel.comedy.postValue(comedy.await())
                this@HomeViewModel.drama.postValue(drama.await())
                this@HomeViewModel.adventure.postValue(adventure.await())
                val timeEnd = System.currentTimeMillis()
                println("Here - TimeEnd: $timeEnd")
                println("Here - TotalTime: ${timeEnd - timeStart}")
                loading.postValue(true)
            } catch (e: Exception) {
                trending.postValue(arrayListOf())
                loading.postValue(true)
            }
        }
    }
}
