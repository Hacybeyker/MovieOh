package com.hacybeyker.movieoh.ui.home

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hacybeyker.movieoh.R
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
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class HomeViewModel
    @Inject
    constructor(
        private val trendingUseCase: TrendingUseCase,
        private val upcomingUseCase: UpcomingUseCase,
        private val discoverUseCase: DiscoverUseCase,
        @param:Named(DISPATCHER_IO) private val dispatcherIO: CoroutineDispatcher,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(HomeUiState())
        val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

        private val sources =
            listOf(
                SectionSource(R.string.trending) { trendingUseCase.fetchTrendingMovie(PAGE_STAR) },
                SectionSource(R.string.action) { discoverUseCase.fetchDiscover(PAGE_STAR, ACTION) },
                SectionSource(R.string.animation) { discoverUseCase.fetchDiscover(PAGE_STAR, ANIMATION) },
                SectionSource(R.string.comedy) { discoverUseCase.fetchDiscover(PAGE_STAR, COMEDY) },
                SectionSource(R.string.drama) { discoverUseCase.fetchDiscover(PAGE_STAR, DRAMA) },
                SectionSource(R.string.adventure) { discoverUseCase.fetchDiscover(PAGE_STAR, ADVENTURE) },
            )

        init {
            loadHome()
        }

        fun loadHome() {
            viewModelScope.launch(dispatcherIO) {
                _uiState.value = HomeUiState(isLoading = true)
                val (featuredMovies, sections) =
                    supervisorScope {
                        val featuredDeferred = async { fetchFeaturedOrEmpty() }
                        val sectionsDeferred = sources.map { source -> async { source.load() } }
                        featuredDeferred.await() to sectionsDeferred.awaitAll().filterNotNull()
                    }
                val dedupedSections = dedupeAcrossSections(featuredMovies, sections)
                _uiState.value =
                    HomeUiState(
                        isLoading = false,
                        featuredMovies = featuredMovies,
                        sections = dedupedSections,
                        isError = dedupedSections.isEmpty() && featuredMovies.isEmpty(),
                    )
            }
        }

        // Popular multi-genre movies top every genre row TMDB returns, so the page would
        // repeat them. Like streaming page-construction algorithms, the first row to show
        // a movie keeps it and later rows skip it; the hero carousel claims its movies first.
        private fun dedupeAcrossSections(
            featured: List<MovieEntity>,
            sections: List<HomeSection>,
        ): List<HomeSection> {
            val seenIds = featured.map { it.id }.toMutableSet()
            return sections.mapNotNull { section ->
                val uniqueMovies = section.movies.filter { seenIds.add(it.id) }
                if (uniqueMovies.isEmpty()) null else section.copy(movies = uniqueMovies)
            }
        }

        private suspend fun fetchFeaturedOrEmpty(): List<MovieEntity> =
            try {
                upcomingUseCase.fetchUpcoming(PAGE_STAR)
            } catch (expected: CancellationException) {
                throw expected
            } catch (_: Exception) {
                emptyList()
            }

        private suspend fun SectionSource.load(): HomeSection? =
            try {
                HomeSection(titleRes, fetch())
            } catch (expected: CancellationException) {
                throw expected
            } catch (_: Exception) {
                null
            }

        private data class SectionSource(
            @param:StringRes val titleRes: Int,
            val fetch: suspend () -> List<MovieEntity>,
        )
    }
