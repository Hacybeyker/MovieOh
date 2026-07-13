package com.hacybeyker.movieoh.ui.movie

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hacybeyker.movieoh.commons.base.NetworkResult
import com.hacybeyker.movieoh.di.module.CoroutineModule.Companion.DISPATCHER_IO
import com.hacybeyker.movieoh.domain.usecase.CreditsUseCase
import com.hacybeyker.movieoh.domain.usecase.MovieUseCase
import com.hacybeyker.movieoh.domain.usecase.PlatformsUseCase
import com.hacybeyker.movieoh.domain.usecase.SimilarUseCase
import com.hacybeyker.movieoh.ui.navigation.MovieOhDestinations
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class MovieViewModel
    @Inject
    constructor(
        private val movieUseCase: MovieUseCase,
        private val creditsUseCase: CreditsUseCase,
        private val similarUseCase: SimilarUseCase,
        private val platformsUseCase: PlatformsUseCase,
        @param:Named(DISPATCHER_IO) private val dispatcherIO: CoroutineDispatcher,
        savedStateHandle: SavedStateHandle,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(MovieUiState())
        val uiState: StateFlow<MovieUiState> = _uiState.asStateFlow()

        init {
            savedStateHandle.get<Int>(MovieOhDestinations.MOVIE_ID_ARG)?.let { loadMovie(it) }
        }

        fun loadMovie(movieId: Int) {
            viewModelScope.launch(dispatcherIO) {
                _uiState.value = MovieUiState(isLoading = true)
                try {
                    val state =
                        coroutineScope {
                            val movie = async { movieUseCase.fetchMovie(movieId) }
                            val credits = async { creditsUseCase.fetchCredits(movieId) }
                            val similar = async { similarUseCase.fetchSimilar(movieId) }
                            MovieUiState(
                                isLoading = false,
                                movie = movie.await(),
                                cast = credits.await().cast,
                                similar = similar.await(),
                            )
                        }
                    _uiState.value = state
                    state.movie?.let { loadPlatforms(it.title) }
                } catch (expected: CancellationException) {
                    throw expected
                } catch (_: Exception) {
                    _uiState.value = MovieUiState(isLoading = false)
                }
            }
        }

        private fun loadPlatforms(title: String) {
            viewModelScope.launch(dispatcherIO) {
                try {
                    when (val result = platformsUseCase.getPlatforms(title)) {
                        is NetworkResult.Success ->
                            _uiState.update { it.copy(platforms = result.data.orEmpty()) }

                        is NetworkResult.Error ->
                            _uiState.update { it.copy(platformsErrorMessage = result.message) }

                        is NetworkResult.Loading -> Unit
                    }
                } catch (expected: CancellationException) {
                    throw expected
                } catch (error: Exception) {
                    _uiState.update { it.copy(platformsErrorMessage = error.message) }
                }
            }
        }
    }
