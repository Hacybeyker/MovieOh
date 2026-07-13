package com.hacybeyker.movieoh.ui.favorites

import com.hacybeyker.movieoh.domain.usecase.FavoriteUseCase
import com.hacybeyker.movieoh.domain.usecase.MovieUseCase
import com.hacybeyker.movieoh.utils.TestCoroutineRule
import com.hacybeyker.movieoh.utils.mockMovieEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class FavoritesViewModelTest {
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val mockFavoriteUseCase: FavoriteUseCase = mock()

    private val mockMovieUseCase: MovieUseCase = mock()

    private fun buildViewModel(): FavoritesViewModel =
        FavoritesViewModel(
            favoriteUseCase = mockFavoriteUseCase,
            movieUseCase = mockMovieUseCase,
            dispatcherIO = testCoroutineRule.testCoroutineDispatcher,
        )

    @Test
    fun `GIVEN no favorites WHEN viewmodel is created THEN uiState exposes an empty list`() {
        testCoroutineRule.runBlockingTest {
            // GIVEN
            whenever(mockFavoriteUseCase.observeFavorites()).thenReturn(flowOf(emptySet()))

            // WHEN
            val viewModel = buildViewModel()
            advanceUntilIdle()

            // THEN
            val uiState = viewModel.uiState.value
            assertFalse(uiState.isLoading)
            assertTrue(uiState.movies.isEmpty())
        }
    }

    @Test
    fun `GIVEN favorite ids WHEN viewmodel is created THEN uiState exposes the hydrated movies`() {
        testCoroutineRule.runBlockingTest {
            // GIVEN
            whenever(mockFavoriteUseCase.observeFavorites()).thenReturn(flowOf(setOf(mockMovieEntity.id)))
            doReturn(mockMovieEntity).whenever(mockMovieUseCase).fetchMovie(mockMovieEntity.id)

            // WHEN
            val viewModel = buildViewModel()
            advanceUntilIdle()

            // THEN
            val uiState = viewModel.uiState.value
            assertFalse(uiState.isLoading)
            assertEquals(listOf(mockMovieEntity), uiState.movies)
        }
    }

    @Test
    fun `GIVEN a favorite fails to hydrate WHEN viewmodel is created THEN it is skipped`() {
        testCoroutineRule.runBlockingTest {
            // GIVEN
            whenever(mockFavoriteUseCase.observeFavorites()).thenReturn(flowOf(setOf(99)))
            doAnswer { error("network") }.whenever(mockMovieUseCase).fetchMovie(99)

            // WHEN
            val viewModel = buildViewModel()
            advanceUntilIdle()

            // THEN
            val uiState = viewModel.uiState.value
            assertFalse(uiState.isLoading)
            assertTrue(uiState.movies.isEmpty())
        }
    }
}
