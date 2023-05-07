package com.hacybeyker.movieoh.data.repository

import com.hacybeyker.movieoh.commons.base.NetworkResult
import com.hacybeyker.movieoh.data.datasource.PlatformsDataSource
import com.hacybeyker.movieoh.utils.TestCoroutineRule
import com.hacybeyker.movieoh.utils.mockPlatformsEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

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
    fun `WHEN THEN`() {
        testCoroutineRule.runBlockingTest {
            // WHEN
            val mockTitle = anyString()
            whenever(mockDataSource.getPlatforms(mockTitle)).doReturn(
                NetworkResult.Success(mockPlatformsEntity)
            )

            val resultData = sutRepositoryData.getPlatforms(mockTitle)

            // THEN
            assertNotNull(sutRepositoryData)
            assertNotNull(resultData)
            verify(mockDataSource).getPlatforms(mockTitle)
        }
    }
}
