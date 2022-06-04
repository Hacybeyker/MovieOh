package com.hacybeyker.movieoh.data.repository

import com.hacybeyker.movieoh.data.datasource.DiscoverDataSource
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
class DiscoverRepositoryDataTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val mockDiscoverDataSource: DiscoverDataSource = mock()

    private val mockMovieEntity: List<MovieEntity> = mock()

    private lateinit var sutDiscoverRepositoryData: DiscoverRepositoryData

    @Before
    fun setup() {
        sutDiscoverRepositoryData = DiscoverRepositoryData(mockDiscoverDataSource)
    }

    @Test
    fun `when call fetchDiscover then datasource call fetchDiscover`() {
        testCoroutineRule.runBlockingTest {
            whenever(
                mockDiscoverDataSource.fetchDiscover(
                    anyInt(),
                    anyInt()
                )
            ).doReturn(mockMovieEntity)

            val resultData = sutDiscoverRepositoryData.fetchDiscover(anyInt(), anyInt())

            assertNotNull(sutDiscoverRepositoryData)
            assertNotNull(resultData)
            verify(mockDiscoverDataSource, times(1)).fetchDiscover(anyInt(), anyInt())
        }
    }
}
