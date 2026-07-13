package com.hacybeyker.movieoh.ui.movie

import androidx.lifecycle.SavedStateHandle
import com.hacybeyker.movieoh.commons.base.NetworkResult
import com.hacybeyker.movieoh.domain.usecase.CreditsUseCase
import com.hacybeyker.movieoh.domain.usecase.MovieUseCase
import com.hacybeyker.movieoh.domain.usecase.PlatformsUseCase
import com.hacybeyker.movieoh.domain.usecase.SimilarUseCase
import com.hacybeyker.movieoh.ui.navigation.MovieOhDestinations
import com.hacybeyker.movieoh.utils.TestCoroutineRule
import com.hacybeyker.movieoh.utils.mockCreditEntity
import com.hacybeyker.movieoh.utils.mockMovieEntity
import com.hacybeyker.movieoh.utils.mockPlatformsEntity
import com.hacybeyker.movieoh.utils.mockSimilarMovies
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class MovieViewModelTest {
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val mockMovieUseCase: MovieUseCase = mock()

    private val mockCreditsUseCase: CreditsUseCase = mock()

    private val mockSimilarUseCase: SimilarUseCase = mock()

    private val mockPlatformsUseCase: PlatformsUseCase = mock()

    private fun buildViewModel(movieId: Int? = mockMovieEntity.id): MovieViewModel =
        MovieViewModel(
            movieUseCase = mockMovieUseCase,
            creditsUseCase = mockCreditsUseCase,
            similarUseCase = mockSimilarUseCase,
            platformsUseCase = mockPlatformsUseCase,
            dispatcherIO = testCoroutineRule.testCoroutineDispatcher,
            savedStateHandle =
                movieId?.let {
                    SavedStateHandle(mapOf(MovieOhDestinations.MOVIE_ID_ARG to it))
                } ?: SavedStateHandle(),
        )

    @Test
    fun `GIVEN a movie id WHEN viewmodel is created THEN uiState exposes movie detail`() {
        testCoroutineRule.runBlockingTest {
            // GIVEN
            whenever(mockMovieUseCase.fetchMovie(mockMovieEntity.id)).doReturn(mockMovieEntity)
            whenever(mockCreditsUseCase.fetchCredits(mockMovieEntity.id)).doReturn(mockCreditEntity)
            whenever(mockSimilarUseCase.fetchSimilar(mockMovieEntity.id)).doReturn(mockSimilarMovies)
            whenever(mockPlatformsUseCase.getPlatforms(mockMovieEntity.title)).doReturn(
                NetworkResult.Success(mockPlatformsEntity),
            )

            // WHEN
            val viewModel = buildViewModel()
            assertTrue(viewModel.uiState.value.isLoading)
            advanceUntilIdle()

            // THEN
            val uiState = viewModel.uiState.value
            assertFalse(uiState.isLoading)
            assertNotNull(uiState.movie)
            assertEquals(mockMovieEntity.id, uiState.movie?.id)
            assertEquals(mockCreditEntity.cast.size, uiState.cast.size)
            assertEquals(mockSimilarMovies.size, uiState.similar.size)
            assertEquals(mockPlatformsEntity.size, uiState.platforms.size)
            verify(mockMovieUseCase, times(1)).fetchMovie(mockMovieEntity.id)
            verify(mockPlatformsUseCase, times(1)).getPlatforms(mockMovieEntity.title)
        }
    }

    @Test
    fun `GIVEN platforms error WHEN viewmodel is created THEN uiState exposes error message`() {
        testCoroutineRule.runBlockingTest {
            // GIVEN
            val errorMessage = "platforms unavailable"
            whenever(mockMovieUseCase.fetchMovie(mockMovieEntity.id)).doReturn(mockMovieEntity)
            whenever(mockCreditsUseCase.fetchCredits(mockMovieEntity.id)).doReturn(mockCreditEntity)
            whenever(mockSimilarUseCase.fetchSimilar(mockMovieEntity.id)).doReturn(mockSimilarMovies)
            whenever(mockPlatformsUseCase.getPlatforms(mockMovieEntity.title)).doReturn(
                NetworkResult.Error(errorMessage),
            )

            // WHEN
            val viewModel = buildViewModel()
            advanceUntilIdle()

            // THEN
            val uiState = viewModel.uiState.value
            assertTrue(uiState.platforms.isEmpty())
            assertEquals(errorMessage, uiState.platformsErrorMessage)
        }
    }

    @Test
    fun `GIVEN platforms exception WHEN viewmodel is created THEN uiState exposes platforms error message`() {
        testCoroutineRule.runBlockingTest {
            // GIVEN
            whenever(mockMovieUseCase.fetchMovie(mockMovieEntity.id)).doReturn(mockMovieEntity)
            whenever(mockCreditsUseCase.fetchCredits(mockMovieEntity.id)).doReturn(mockCreditEntity)
            whenever(mockSimilarUseCase.fetchSimilar(mockMovieEntity.id)).doReturn(mockSimilarMovies)
            whenever(mockPlatformsUseCase.getPlatforms(mockMovieEntity.title))
                .doAnswer { error("platforms down") }

            // WHEN
            val viewModel = buildViewModel()
            advanceUntilIdle()

            // THEN
            val uiState = viewModel.uiState.value
            assertNotNull(uiState.movie)
            assertTrue(uiState.platforms.isEmpty())
            assertEquals("platforms down", uiState.platformsErrorMessage)
        }
    }

    @Test
    fun `GIVEN no movie id WHEN viewmodel is created THEN nothing is loaded`() {
        testCoroutineRule.runBlockingTest {
            // WHEN
            val viewModel = buildViewModel(movieId = null)
            advanceUntilIdle()

            // THEN
            verifyNoInteractions(
                mockMovieUseCase,
                mockCreditsUseCase,
                mockSimilarUseCase,
                mockPlatformsUseCase,
            )
            assertNull(viewModel.uiState.value.movie)
        }
    }

    @Test
    fun `GIVEN an exception WHEN viewmodel is created THEN uiState exposes empty detail`() {
        testCoroutineRule.runBlockingTest {
            // GIVEN
            whenever(mockMovieUseCase.fetchMovie(mockMovieEntity.id)).doAnswer { error("network") }
            whenever(mockCreditsUseCase.fetchCredits(mockMovieEntity.id)).doReturn(mockCreditEntity)
            whenever(mockSimilarUseCase.fetchSimilar(mockMovieEntity.id)).doReturn(mockSimilarMovies)

            // WHEN
            val viewModel = buildViewModel()
            advanceUntilIdle()

            // THEN
            val uiState = viewModel.uiState.value
            assertFalse(uiState.isLoading)
            assertNull(uiState.movie)
            verify(mockMovieUseCase, times(1)).fetchMovie(mockMovieEntity.id)
        }
    }
}
