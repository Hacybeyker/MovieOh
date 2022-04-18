package com.hacybeyker.movieoh.ui.home.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.hacybeyker.movieoh.commons.exception.ApiException
import com.hacybeyker.movieoh.domain.entity.MovieEntity
import com.hacybeyker.movieoh.domain.usecase.TrendingUseCase
import com.hacybeyker.movieoh.utils.TestCoroutineRule
import com.hacybeyker.movieoh.utils.getOrAwaitValue
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
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
class HomeViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val mockMovieEntityList: List<MovieEntity> = mock()

    private val mockTrendingUseCase: TrendingUseCase = mock()

    private val mockDispatcherIO: CoroutineDispatcher by lazy { Dispatchers.IO }

    private lateinit var sutHomeViewModel: HomeViewModel

    @Before
    fun setup() {
        sutHomeViewModel = HomeViewModel(mockTrendingUseCase, mockDispatcherIO)
    }

    @Test
    fun `GIVEN a mockMovieEntityList WHEN call fetchTrendingMovie THEN usecase call fetchTrendingMovie`() {
        testCoroutineRule.runBlockingTest {
            whenever(mockTrendingUseCase.fetchTrendingMovie(anyInt())).doReturn(mockMovieEntityList)
            whenever(mockMovieEntityList.size).doReturn(2)

            sutHomeViewModel.fetchTrendingMovie()
            val trending = sutHomeViewModel.trendingLiveData.getOrAwaitValue()
            val loading = sutHomeViewModel.loadingLiveData.getOrAwaitValue()

            assertNotNull(loading)
            assertNotNull(trending)
            assertEquals(2, trending.size)
            verify(mockTrendingUseCase, times(numInvocations = 1)).fetchTrendingMovie(anyInt())
        }
    }

    @Test
    fun `GIVEN a exception WHEN call fetchTrendingMovie THEN generate throw exception`() {
        testCoroutineRule.runBlockingTest {
            whenever(mockTrendingUseCase.fetchTrendingMovie(anyInt())).doAnswer { throw ApiException() }

            sutHomeViewModel.fetchTrendingMovie()
            val trending = sutHomeViewModel.trendingLiveData.getOrAwaitValue()
            val loading = sutHomeViewModel.loadingLiveData.getOrAwaitValue()

            assertNotNull(loading)
            assertNotNull(trending)
            assertEquals(arrayListOf<MovieEntity>(), trending)
            verify(mockTrendingUseCase, times(numInvocations = 1)).fetchTrendingMovie(anyInt())
        }
    }
}
