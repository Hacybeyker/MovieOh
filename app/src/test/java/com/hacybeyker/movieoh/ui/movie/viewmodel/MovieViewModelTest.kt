package com.hacybeyker.movieoh.ui.movie.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.hacybeyker.movieoh.commons.base.NetworkResult
import com.hacybeyker.movieoh.commons.exception.ApiException
import com.hacybeyker.movieoh.domain.usecase.CreditsUseCase
import com.hacybeyker.movieoh.domain.usecase.MovieUseCase
import com.hacybeyker.movieoh.domain.usecase.PlatformsUseCase
import com.hacybeyker.movieoh.domain.usecase.SimilarUseCase
import com.hacybeyker.movieoh.utils.TestCoroutineRule
import com.hacybeyker.movieoh.utils.getOrAwaitValue
import com.hacybeyker.movieoh.utils.mockCreditEntity
import com.hacybeyker.movieoh.utils.mockMovieEntity
import com.hacybeyker.movieoh.utils.mockPlatformsEntity
import com.hacybeyker.movieoh.utils.mockSimilarMovies
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class MovieViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val mockMovieUseCase: MovieUseCase = mock()

    private val mockCreditsUseCase: CreditsUseCase = mock()

    private val mockSimilarUseCase: SimilarUseCase = mock()

    private val mockPlatformsUseCase: PlatformsUseCase = mock()

    private val mockDispatcherIO: CoroutineDispatcher by lazy { Dispatchers.IO }

    private lateinit var sutMovieViewModel: MovieViewModel

    @Before
    fun setup() {
        sutMovieViewModel = MovieViewModel(
            movieUseCase = mockMovieUseCase,
            creditsUseCase = mockCreditsUseCase,
            similarUseCase = mockSimilarUseCase,
            platformsUseCase = mockPlatformsUseCase,
            dispatcherIO = mockDispatcherIO
        )
    }

    @Test
    fun `GIVEN a mockMovieEntityList WHEN call init THEN call all usecase`() {
        testCoroutineRule.runBlockingTest {
            // Given
            whenever(mockSimilarUseCase.fetchSimilar(mockMovieEntity.id)).doReturn(mockSimilarMovies)
            whenever(mockMovieUseCase.fetchMovie(mockMovieEntity.id)).doReturn(mockMovieEntity)
            whenever(mockCreditsUseCase.fetchCredits(mockMovieEntity.id)).doReturn(mockCreditEntity)
            whenever(mockPlatformsUseCase.getPlatforms(mockMovieEntity.title)).doReturn(
                NetworkResult.Success(mockPlatformsEntity)
            )

            // When
            sutMovieViewModel.init(mockMovieEntity)
            val similarLiveData = sutMovieViewModel.similarLiveData.getOrAwaitValue()
            val movieLiveData = sutMovieViewModel.movieLiveData.getOrAwaitValue()
            val creditsLiveData = sutMovieViewModel.creditsLiveData.getOrAwaitValue()
            val platformsLiveData = sutMovieViewModel.platforms.getOrAwaitValue()
            val loadingLiveData = sutMovieViewModel.loadingLiveData.getOrAwaitValue()

            // Then
            assertNotNull(sutMovieViewModel)
            assertNotNull(mockMovieEntity)
            assertNotNull(similarLiveData)
            assertNotNull(movieLiveData)
            assertNotNull(creditsLiveData)
            assertNotNull(platformsLiveData)
            assertNotNull(loadingLiveData)

            assertEquals(mockSimilarMovies, similarLiveData)
            assertEquals(mockMovieEntity, movieLiveData)
            assertEquals(mockCreditEntity, creditsLiveData)
            assertEquals(mockPlatformsEntity, platformsLiveData.data)

            verify(mockSimilarUseCase, times(numInvocations = 1)).fetchSimilar(mockMovieEntity.id)
            verify(mockMovieUseCase, times(numInvocations = 1)).fetchMovie(mockMovieEntity.id)
            verify(mockCreditsUseCase, times(numInvocations = 1)).fetchCredits(mockMovieEntity.id)
            verify(mockPlatformsUseCase).getPlatforms(mockMovieEntity.title)
        }
    }

    @Test
    fun `GIVEN a exception WHEN call init THEN generate throw exception`() {
        testCoroutineRule.runBlockingTest {
            // Given
            whenever(mockSimilarUseCase.fetchSimilar(mockMovieEntity.id)).doAnswer { throw ApiException() }

            // When
            try {
                sutMovieViewModel.init(mockMovieEntity)
            } catch (e: Exception) {
                val similarLiveData = sutMovieViewModel.similarLiveData.getOrAwaitValue()
                val loadingLiveData = sutMovieViewModel.loadingLiveData.getOrAwaitValue()
                // Then
                assertNotNull(sutMovieViewModel)
                assertNotNull(similarLiveData)
                assertNotNull(loadingLiveData)
                assertFalse(loadingLiveData)
            }
        }
    }
}
