package com.hacybeyker.movieoh.data.datasource.remote

import com.hacybeyker.movieoh.commons.exception.ApiException
import com.hacybeyker.movieoh.data.api.MovieApi
import com.hacybeyker.movieoh.data.model.remote.response.MovieResponseModel
import com.hacybeyker.movieoh.utils.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
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
class MovieDataSourceRemoteTest {
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val mockMovieApi: MovieApi = mock()

    private val mockMovieResponseModel: MovieResponseModel = mock()

    private lateinit var sutMovieDataSourceRemote: MovieDataSourceRemote

    @Before
    fun setup() {
        sutMovieDataSourceRemote = MovieDataSourceRemote(mockMovieApi)
    }

    @Test
    fun verifyApiSuccess() {
        testCoroutineRule.runBlockingTest {
            whenever(mockMovieApi.fetchMovie(anyInt())).doReturn(mockMovieResponseModel)

            val resultData = sutMovieDataSourceRemote.fetchMovie(anyInt())

            Assert.assertNotNull(sutMovieDataSourceRemote)
            Assert.assertNotNull(resultData)
            verify(mockMovieApi, times(1)).fetchMovie(anyInt())
        }
    }

    @Test
    fun verifyApiError() {
        testCoroutineRule.runBlockingTest {
            try {
                whenever(mockMovieApi.fetchMovie(anyInt())).doThrow(RuntimeException(ApiException()))

                sutMovieDataSourceRemote.fetchMovie(anyInt())
            } catch (e: Exception) {
                (e.cause as? ApiException).let {
                    Assert.assertEquals(e.cause, it)
                    verify(mockMovieApi, times(1)).fetchMovie(anyInt())
                }
            }
        }
    }
}
