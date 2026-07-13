package com.hacybeyker.movieoh.data.repository

import com.hacybeyker.movieoh.commons.base.NetworkResult
import com.hacybeyker.movieoh.data.datasource.PlatformsDataSource
import com.hacybeyker.movieoh.utils.TestCoroutineRule
import com.hacybeyker.movieoh.utils.mockPlatformsEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

private const val MOVIE_TITLE = "Interstellar"

@ExperimentalCoroutinesApi
class PlatformsRepositoryDataTest {
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val mockDataSource: PlatformsDataSource = mock()

    private lateinit var sutRepositoryData: PlatformsRepositoryData

    @Before
    fun setup() {
        sutRepositoryData = PlatformsRepositoryData(mockDataSource)
    }

    @Test
    fun `GIVEN datasource success WHEN getPlatforms THEN result is delegated with data`() {
        testCoroutineRule.runBlockingTest {
            // GIVEN
            whenever(mockDataSource.getPlatforms(MOVIE_TITLE)).doReturn(
                NetworkResult.Success(mockPlatformsEntity),
            )

            // WHEN
            val result = sutRepositoryData.getPlatforms(MOVIE_TITLE)

            // THEN
            assertTrue(result is NetworkResult.Success)
            assertEquals(mockPlatformsEntity, (result as NetworkResult.Success).data)
            verify(mockDataSource).getPlatforms(MOVIE_TITLE)
        }
    }

    @Test
    fun `GIVEN datasource error WHEN getPlatforms THEN error is delegated`() {
        testCoroutineRule.runBlockingTest {
            // GIVEN
            val errorMessage = "platforms unavailable"
            whenever(mockDataSource.getPlatforms(MOVIE_TITLE)).doReturn(
                NetworkResult.Error(errorMessage),
            )

            // WHEN
            val result = sutRepositoryData.getPlatforms(MOVIE_TITLE)

            // THEN
            assertTrue(result is NetworkResult.Error)
            assertEquals(errorMessage, (result as NetworkResult.Error).message)
        }
    }
}
