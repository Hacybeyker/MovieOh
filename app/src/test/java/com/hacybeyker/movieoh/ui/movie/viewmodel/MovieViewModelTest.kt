package com.hacybeyker.movieoh.ui.movie.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.hacybeyker.movieoh.commons.exception.ApiException
import com.hacybeyker.movieoh.data.model.remote.response.CreditsResponseModel
import com.hacybeyker.movieoh.data.model.remote.response.MovieResponseModel
import com.hacybeyker.movieoh.data.model.remote.response.ResultResponseModel
import com.hacybeyker.movieoh.data.model.remote.response.toCreditsEntity
import com.hacybeyker.movieoh.data.model.remote.response.toEntity
import com.hacybeyker.movieoh.data.model.remote.response.toMovieResponseModelList
import com.hacybeyker.movieoh.domain.entity.CreditsEntity
import com.hacybeyker.movieoh.domain.entity.MovieEntity
import com.hacybeyker.movieoh.domain.usecase.CreditsUseCase
import com.hacybeyker.movieoh.domain.usecase.MovieUseCase
import com.hacybeyker.movieoh.domain.usecase.SimilarUseCase
import com.hacybeyker.movieoh.utils.JSONFileLoader
import com.hacybeyker.movieoh.utils.TestCoroutineRule
import com.hacybeyker.movieoh.utils.getOrAwaitValue
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
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

    private val mockDispatcherIO: CoroutineDispatcher by lazy { Dispatchers.IO }

    private val mockMovieEntity: MovieEntity by lazy {
        JSONFileLoader().loadJsonString<MovieResponseModel>("response_model_movie.json")
            .toEntity()
    }

    private val mockCreditEntity: CreditsEntity by lazy {
        JSONFileLoader().loadJsonString<CreditsResponseModel>("response_model_credits.json")
            .toCreditsEntity()
    }

    private val mockSimilarMovies: List<MovieEntity> by lazy {
        JSONFileLoader().loadJsonString<ResultResponseModel>("response_model_similar.json")
            .toMovieResponseModelList()
    }

    private lateinit var sutMovieViewModel: MovieViewModel

    @Before
    fun setup() {
        sutMovieViewModel = MovieViewModel(
            movieUseCase = mockMovieUseCase,
            creditsUseCase = mockCreditsUseCase,
            similarUseCase = mockSimilarUseCase,
            dispatcherIO = mockDispatcherIO
        )
    }

    @Test
    fun `GIVEN a mockMovieEntityList WHEN call init THEN call all usecase`() {
        testCoroutineRule.runBlockingTest {
            val movieId: Int = anyInt()
            // Given
            whenever(mockSimilarUseCase.fetchSimilar(movieId)).doReturn(mockSimilarMovies)
            whenever(mockMovieUseCase.fetchMovie(movieId)).doReturn(mockMovieEntity)
            whenever(mockCreditsUseCase.fetchCredits(movieId)).doReturn(mockCreditEntity)

            // When
            sutMovieViewModel.init(movieId)
            val similarLiveData = sutMovieViewModel.similarLiveData.getOrAwaitValue()
            val movieLiveData = sutMovieViewModel.movieLiveData.getOrAwaitValue()
            val creditsLiveData = sutMovieViewModel.creditsLiveData.getOrAwaitValue()
            val loadingLiveData = sutMovieViewModel.loadingLiveData.getOrAwaitValue()

            // Then
            assertNotNull(sutMovieViewModel)
            assertNotNull(movieId)
            assertNotNull(similarLiveData)
            assertNotNull(movieLiveData)
            assertNotNull(creditsLiveData)
            assertNotNull(loadingLiveData)

            assertEquals(mockSimilarMovies, similarLiveData)
            assertEquals(mockMovieEntity, movieLiveData)
            assertEquals(mockCreditEntity, creditsLiveData)

            verify(mockSimilarUseCase, times(numInvocations = 1)).fetchSimilar(movieId)
            verify(mockMovieUseCase, times(numInvocations = 1)).fetchMovie(movieId)
            verify(mockCreditsUseCase, times(numInvocations = 1)).fetchCredits(movieId)
        }
    }

    @Test
    fun `GIVEN a exception WHEN call init THEN generate throw exception`() {
        testCoroutineRule.runBlockingTest {
            // Given
            val movieId: Int = anyInt()
            whenever(mockSimilarUseCase.fetchSimilar(movieId)).doAnswer { throw ApiException() }

            // When
            try {
                sutMovieViewModel.init(movieId)
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
