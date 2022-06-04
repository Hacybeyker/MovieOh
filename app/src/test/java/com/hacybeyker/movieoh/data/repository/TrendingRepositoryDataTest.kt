package com.hacybeyker.movieoh.data.repository

import com.hacybeyker.movieoh.data.datasource.TrendingDataSource
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
class TrendingRepositoryDataTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val mockDataSource: TrendingDataSource = mock()

    private val mockListMovieEntity: List<MovieEntity> = mock()

    private lateinit var sutRepositoryData: TrendingRepositoryData

    @Before
    fun setup() {
        sutRepositoryData = TrendingRepositoryData(mockDataSource)
    }

    @Test
    fun `when call fetchTrendingMovie then datasource call fetchTrendingMovie`() {
        testCoroutineRule.runBlockingTest {
            whenever(mockDataSource.fetchTrendingMovie(anyInt())).doReturn(mockListMovieEntity)

            val resultData = sutRepositoryData.fetchTrendingMovie(anyInt())

            assertNotNull(sutRepositoryData)
            assertNotNull(resultData)
            verify(mockDataSource, times(numInvocations = 1)).fetchTrendingMovie(anyInt())
        }
    }
}
