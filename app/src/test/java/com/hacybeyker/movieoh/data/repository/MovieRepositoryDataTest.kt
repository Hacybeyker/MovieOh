package com.hacybeyker.movieoh.data.repository

import com.hacybeyker.movieoh.data.datasource.MovieDataSource
import com.hacybeyker.movieoh.domain.entity.MovieEntity
import com.hacybeyker.movieoh.utils.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class MovieRepositoryDataTest {
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val mockMovieDataSource: MovieDataSource = mock()

    private val mockMovieEntity: MovieEntity = mock()

    private lateinit var sutMovieRepositoryData: MovieRepositoryData

    @Before
    fun setup() {
        sutMovieRepositoryData = MovieRepositoryData(mockMovieDataSource)
    }

    @Test
    fun `when call fetchMovie then datasource call fetchMovie`() {
        testCoroutineRule.runBlockingTest {
            whenever(mockMovieDataSource.fetchMovie(anyInt())).doReturn(mockMovieEntity)

            val resultData = sutMovieRepositoryData.fetchMovie(anyInt())

            assertNotNull(sutMovieRepositoryData)
            assertNotNull(resultData)
            verify(mockMovieDataSource, times(1)).fetchMovie(anyInt())
        }
    }
}
