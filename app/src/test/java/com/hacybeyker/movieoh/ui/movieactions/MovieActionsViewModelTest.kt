package com.hacybeyker.movieoh.ui.movieactions

import com.hacybeyker.movieoh.domain.usecase.FavoriteUseCase
import com.hacybeyker.movieoh.utils.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class MovieActionsViewModelTest {
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val mockFavoriteUseCase: FavoriteUseCase = mock()

    private fun buildViewModel(): MovieActionsViewModel = MovieActionsViewModel(favoriteUseCase = mockFavoriteUseCase)

    @Test
    fun `GIVEN repository emits favorites WHEN viewmodel is created THEN uiState exposes them`() {
        testCoroutineRule.runBlockingTest {
            // GIVEN
            whenever(mockFavoriteUseCase.observeFavorites()).thenReturn(flowOf(setOf(1, 2, 3)))

            // WHEN
            val viewModel = buildViewModel()
            // WhileSubscribed only starts the upstream flow once someone collects
            val collectJob = launch { viewModel.favorites.collect {} }
            advanceUntilIdle()

            // THEN
            assertEquals(setOf(1, 2, 3), viewModel.favorites.value)
            collectJob.cancel()
        }
    }

    @Test
    fun `WHEN toggleFavorite THEN it delegates to the use case`() {
        testCoroutineRule.runBlockingTest {
            // GIVEN
            whenever(mockFavoriteUseCase.observeFavorites()).thenReturn(flowOf(emptySet()))
            val viewModel = buildViewModel()
            advanceUntilIdle()

            // WHEN
            viewModel.toggleFavorite(42)
            advanceUntilIdle()

            // THEN
            verify(mockFavoriteUseCase).toggleFavorite(42)
        }
    }
}
