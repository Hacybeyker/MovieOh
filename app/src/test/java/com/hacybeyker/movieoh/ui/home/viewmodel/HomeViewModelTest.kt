package com.hacybeyker.movieoh.ui.home.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.hacybeyker.movieoh.domain.entity.MovieEntity
import com.hacybeyker.movieoh.domain.usecase.DiscoverUseCase
import com.hacybeyker.movieoh.domain.usecase.TrendingUseCase
import com.hacybeyker.movieoh.domain.usecase.UpcomingUseCase
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
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class HomeViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val mockMovieEntityList: List<MovieEntity> = mock()

    private val mockTrendingUseCase: TrendingUseCase = mock()

    private val mockUpcomingUseCase: UpcomingUseCase = mock()

    private val mockDiscoverUseCase: DiscoverUseCase = mock()

    private val mockDispatcherIO: CoroutineDispatcher by lazy { Dispatchers.Unconfined }

    private lateinit var sutHomeViewModel: HomeViewModel

    @Before
    fun setup() {
        sutHomeViewModel =
            HomeViewModel(
                mockTrendingUseCase,
                mockUpcomingUseCase,
                mockDiscoverUseCase,
                mockDispatcherIO,
            )
    }

    @Test
    fun `GIVEN a mockMovieEntityList WHEN call initHome THEN call all usecase`() {
        testCoroutineRule.runBlockingTest {
            // GIVEN
            whenever(mockTrendingUseCase.fetchTrendingMovie(anyInt())).doReturn(mockMovieEntityList)
            whenever(mockUpcomingUseCase.fetchUpcoming(anyInt())).doReturn(mockMovieEntityList)
            whenever(mockDiscoverUseCase.fetchDiscover(anyInt(), anyInt())).doReturn(
                mockMovieEntityList,
            )
            whenever(mockMovieEntityList.size).doReturn(2)

            // WHEN
            sutHomeViewModel.initHome()
            val trending = sutHomeViewModel.trendingLiveData.getOrAwaitValue()
            val upcoming = sutHomeViewModel.upcomingLiveData.getOrAwaitValue()
            val action = sutHomeViewModel.actionLiveData.getOrAwaitValue()
            val animation = sutHomeViewModel.animationLiveData.getOrAwaitValue()
            val comedy = sutHomeViewModel.comedyLiveData.getOrAwaitValue()
            val drama = sutHomeViewModel.dramaLiveData.getOrAwaitValue()
            val adventure = sutHomeViewModel.adventureLiveData.getOrAwaitValue()
            val loading = sutHomeViewModel.loadingLiveData.getOrAwaitValue()

            // THEN
            assertNotNull(trending)
            assertNotNull(upcoming)
            assertNotNull(action)
            assertNotNull(animation)
            assertNotNull(comedy)
            assertNotNull(drama)
            assertNotNull(adventure)
            assertNotNull(loading)

            assertEquals(2, trending.size)
            verify(mockTrendingUseCase, times(numInvocations = 1)).fetchTrendingMovie(anyInt())
        }
    }

    @Test(expected = Exception::class)
    fun `GIVEN a exception WHEN call initHome THEN generate throw exception`() {
        testCoroutineRule.runBlockingTest {
            // GIVEN
            val page: Int = 1
            whenever(mockTrendingUseCase.fetchTrendingMovie(page)).doAnswer { throw Exception("") }

            // WHEN
            sutHomeViewModel.initHome()
            val trending = sutHomeViewModel.trendingLiveData.getOrAwaitValue()
            val loading = sutHomeViewModel.loadingLiveData.getOrAwaitValue()

            // THEN
            assertNotNull(loading)
            assertNotNull(trending)
            assertEquals(arrayListOf<MovieEntity>(), trending)
            verify(mockTrendingUseCase, times(numInvocations = 1)).fetchTrendingMovie(page)
        }
    }
}
