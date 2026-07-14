package com.hacybeyker.movieoh.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hacybeyker.movieoh.di.module.CoroutineModule.Companion.DISPATCHER_IO
import com.hacybeyker.movieoh.domain.entity.MovieEntity
import com.hacybeyker.movieoh.domain.usecase.FavoriteUseCase
import com.hacybeyker.movieoh.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class FavoritesViewModel
    @Inject
    constructor(
        private val favoriteUseCase: FavoriteUseCase,
        private val movieUseCase: MovieUseCase,
        @param:Named(DISPATCHER_IO) private val dispatcherIO: CoroutineDispatcher,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(FavoritesUiState())
        val uiState: StateFlow<FavoritesUiState> = _uiState.asStateFlow()

        init {
            observeFavorites()
        }

        private fun observeFavorites() {
            viewModelScope.launch(dispatcherIO) {
                favoriteUseCase.observeFavorites().collectLatest { ids ->
                    _uiState.value = FavoritesUiState(isLoading = true)
                    val movies =
                        supervisorScope {
                            ids
                                .map { id -> async { fetchMovieOrNull(id) } }
                                .awaitAll()
                                .filterNotNull()
                        }
                    _uiState.value = FavoritesUiState(isLoading = false, movies = movies)
                }
            }
        }

        private suspend fun fetchMovieOrNull(movieId: Int): MovieEntity? =
            try {
                movieUseCase.fetchMovie(movieId)
            } catch (expected: CancellationException) {
                throw expected
            } catch (_: Exception) {
                null
            }
    }
