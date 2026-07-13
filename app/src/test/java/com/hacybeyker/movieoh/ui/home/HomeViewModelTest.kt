package com.hacybeyker.movieoh.ui.home

import com.hacybeyker.movieoh.R
import com.hacybeyker.movieoh.domain.usecase.DiscoverUseCase
import com.hacybeyker.movieoh.domain.usecase.TrendingUseCase
import com.hacybeyker.movieoh.domain.usecase.UpcomingUseCase
import com.hacybeyker.movieoh.utils.TestCoroutineRule
import com.hacybeyker.movieoh.utils.mockSimilarMovies
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

private const val DISCOVER_SECTIONS = 5

private val expectedSectionTitles =
    listOf(
        R.string.upcoming,
        R.string.trending,
        R.string.action,
        R.string.animation,
        R.string.comedy,
        R.string.drama,
        R.string.adventure,
    )

@ExperimentalCoroutinesApi
class HomeViewModelTest {
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val mockTrendingUseCase: TrendingUseCase = mock()

    private val mockUpcomingUseCase: UpcomingUseCase = mock()

    private val mockDiscoverUseCase: DiscoverUseCase = mock()

    private fun buildViewModel(): HomeViewModel =
        HomeViewModel(
            trendingUseCase = mockTrendingUseCase,
            upcomingUseCase = mockUpcomingUseCase,
            discoverUseCase = mockDiscoverUseCase,
            dispatcherIO = testCoroutineRule.testCoroutineDispatcher,
        )

    private suspend fun givenAllSectionsSucceed() {
        doReturn(mockSimilarMovies).whenever(mockTrendingUseCase).fetchTrendingMovie(anyInt())
        doReturn(mockSimilarMovies).whenever(mockUpcomingUseCase).fetchUpcoming(anyInt())
        doReturn(mockSimilarMovies).whenever(mockDiscoverUseCase).fetchDiscover(anyInt(), anyInt())
    }

    private suspend fun givenAllSectionsFail() {
        doAnswer { error("network") }.whenever(mockTrendingUseCase).fetchTrendingMovie(anyInt())
        doAnswer { error("network") }.whenever(mockUpcomingUseCase).fetchUpcoming(anyInt())
        doAnswer { error("network") }.whenever(mockDiscoverUseCase).fetchDiscover(anyInt(), anyInt())
    }

    @Test
    fun `GIVEN all sections succeed WHEN viewmodel is created THEN uiState exposes all sections in order`() {
        testCoroutineRule.runBlockingTest {
            // GIVEN
            givenAllSectionsSucceed()

            // WHEN
            val viewModel = buildViewModel()
            assertTrue(viewModel.uiState.value.isLoading)
            advanceUntilIdle()

            // THEN
            val uiState = viewModel.uiState.value
            assertFalse(uiState.isLoading)
            assertFalse(uiState.isError)
            assertEquals(expectedSectionTitles, uiState.sections.map { it.titleRes })
            assertTrue(uiState.sections.all { it.movies == mockSimilarMovies })
            verify(mockTrendingUseCase, times(1)).fetchTrendingMovie(anyInt())
            verify(mockUpcomingUseCase, times(1)).fetchUpcoming(anyInt())
            verify(mockDiscoverUseCase, times(DISCOVER_SECTIONS)).fetchDiscover(anyInt(), anyInt())
        }
    }

    @Test
    fun `GIVEN one section fails WHEN viewmodel is created THEN remaining sections are shown without error`() {
        testCoroutineRule.runBlockingTest {
            // GIVEN
            givenAllSectionsSucceed()
            whenever(mockTrendingUseCase.fetchTrendingMovie(anyInt())).doAnswer { error("network") }

            // WHEN
            val viewModel = buildViewModel()
            advanceUntilIdle()

            // THEN
            val uiState = viewModel.uiState.value
            assertFalse(uiState.isLoading)
            assertFalse(uiState.isError)
            assertEquals(
                expectedSectionTitles - R.string.trending,
                uiState.sections.map { it.titleRes },
            )
        }
    }

    @Test
    fun `GIVEN all sections fail WHEN viewmodel is created THEN uiState exposes error`() {
        testCoroutineRule.runBlockingTest {
            // GIVEN
            givenAllSectionsFail()

            // WHEN
            val viewModel = buildViewModel()
            advanceUntilIdle()

            // THEN
            val uiState = viewModel.uiState.value
            assertFalse(uiState.isLoading)
            assertTrue(uiState.isError)
            assertTrue(uiState.sections.isEmpty())
        }
    }

    @Test
    fun `GIVEN a failed load WHEN loadHome is retried and succeeds THEN uiState recovers`() {
        testCoroutineRule.runBlockingTest {
            // GIVEN
            givenAllSectionsFail()
            val viewModel = buildViewModel()
            advanceUntilIdle()
            assertTrue(viewModel.uiState.value.isError)

            // WHEN
            givenAllSectionsSucceed()
            viewModel.loadHome()
            advanceUntilIdle()

            // THEN
            val uiState = viewModel.uiState.value
            assertFalse(uiState.isLoading)
            assertFalse(uiState.isError)
            assertEquals(expectedSectionTitles, uiState.sections.map { it.titleRes })
        }
    }
}
