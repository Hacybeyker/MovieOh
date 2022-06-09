package com.hacybeyker.movieoh.data.repository

import com.hacybeyker.movieoh.data.datasource.UpcomingDataSource
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
class UpcomingRepositoryDataTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val mockUpcomingDataSource: UpcomingDataSource = mock()

    private val mockListMovieEntity: List<MovieEntity> = mock()

    private lateinit var sutUpcomingRepositoryData: UpcomingRepositoryData

    @Before
    fun setup() {
        sutUpcomingRepositoryData = UpcomingRepositoryData(mockUpcomingDataSource)
    }

    @Test
    fun `when call fetchUpcoming then datasource call fetchUpcoming`() {
        testCoroutineRule.runBlockingTest {
            whenever(mockUpcomingDataSource.fetchUpcoming(anyInt())).doReturn(mockListMovieEntity)

            val resultData = sutUpcomingRepositoryData.fetchUpcoming(anyInt())

            assertNotNull(sutUpcomingRepositoryData)
            assertNotNull(resultData)
            verify(mockUpcomingDataSource, times(numInvocations = 1)).fetchUpcoming(anyInt())
        }
    }
}
