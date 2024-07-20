package com.hacybeyker.movieoh.data.datasource.remote

import com.hacybeyker.movieoh.commons.exception.ApiException
import com.hacybeyker.movieoh.data.api.TrendingMovieApi
import com.hacybeyker.movieoh.data.model.remote.response.ResultResponseModel
import com.hacybeyker.movieoh.data.model.remote.response.toMovieResponseModelList
import com.hacybeyker.movieoh.domain.entity.MovieEntity
import com.hacybeyker.movieoh.utils.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class TrendingDataSourceRemoteTest {
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val mockApi: TrendingMovieApi = mock()

    private val mockResponse: ResultResponseModel = mock()

    private lateinit var sutTrendingDataSourceRemote: TrendingDataSourceRemote

    @Before
    fun setup() {
        sutTrendingDataSourceRemote = TrendingDataSourceRemote(mockApi)
    }

    @Test
    fun `when call fetchTrendingMovie then api call fetchTrendingMovie`() {
        testCoroutineRule.runBlockingTest {
            whenever(mockApi.fetchTrendingMovie(anyInt())).doReturn(mockResponse)

            val resultResponse = sutTrendingDataSourceRemote.fetchTrendingMovie(anyInt())

            verify(mockApi, times(numInvocations = 1)).fetchTrendingMovie(anyInt())
            verify(mockApi).fetchTrendingMovie(anyInt())
            assertNotNull(resultResponse)
        }
    }

    @Test
    fun `when call fetchTrendingMovie then api call Error`() {
        testCoroutineRule.runBlockingTest {
            try {
                doThrow(RuntimeException("Error", ApiException())).whenever(mockApi)
                    .fetchTrendingMovie(anyInt())
                sutTrendingDataSourceRemote.fetchTrendingMovie(anyInt())
                fail()
            } catch (e: Exception) {
                (e.cause as? ApiException)?.let {
                    assertEquals(e.cause, it)
                    verify(mockApi, times(1)).fetchTrendingMovie(anyInt())
                }
            }
        }
    }

    @Test
    fun `mapper is success`() {
        val mockResponse: ResultResponseModel = mock()
        val movieEntityData = arrayListOf<MovieEntity>()
        whenever(mockResponse.toMovieResponseModelList()).doReturn(movieEntityData)

        val value = mockResponse.toMovieResponseModelList()

        assertNotNull(mockResponse)
        assertEquals(movieEntityData, value)
    }
}
