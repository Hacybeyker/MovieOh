package com.hacybeyker.movieoh.data.datasource.remote

import com.hacybeyker.movieoh.commons.base.NetworkResult
import com.hacybeyker.movieoh.data.api.PlatformsApi
import com.hacybeyker.movieoh.utils.TestCoroutineRule
import com.hacybeyker.movieoh.utils.mockPlatformsModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.util.Locale

private const val MOVIE_ID = 505642

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class PlatformsDataSourceRemoteTest {
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val mockApi: PlatformsApi = mock()

    private lateinit var sutDataSourceRemote: PlatformsDataSourceRemote

    private lateinit var defaultLocale: Locale

    @Before
    fun setup() {
        defaultLocale = Locale.getDefault()
        sutDataSourceRemote = PlatformsDataSourceRemote(mockApi)
    }

    @After
    fun tearDown() {
        Locale.setDefault(defaultLocale)
    }

    @Test
    fun `GIVEN providers for the device country WHEN getPlatforms THEN they are mapped without duplicates`() {
        testCoroutineRule.runBlockingTest {
            // GIVEN
            Locale.setDefault(Locale.US)
            whenever(mockApi.getWatchProviders(MOVIE_ID)).doReturn(mockPlatformsModel)

            // WHEN
            val result = sutDataSourceRemote.getPlatforms(MOVIE_ID)

            // THEN
            assertTrue(result is NetworkResult.Success)
            val platforms = (result as NetworkResult.Success).data.orEmpty()
            assertEquals(listOf("Disney Plus", "Amazon Video"), platforms.map { it.name })
            assertTrue(platforms.all { it.url.isNotEmpty() && it.logoPath.isNotEmpty() })
            verify(mockApi).getWatchProviders(MOVIE_ID)
        }
    }

    @Test
    fun `GIVEN a country without providers WHEN getPlatforms THEN it falls back to US`() {
        testCoroutineRule.runBlockingTest {
            // GIVEN
            Locale.setDefault(Locale.FRANCE)
            whenever(mockApi.getWatchProviders(MOVIE_ID)).doReturn(mockPlatformsModel)

            // WHEN
            val result = sutDataSourceRemote.getPlatforms(MOVIE_ID)

            // THEN
            assertTrue(result is NetworkResult.Success)
            val platforms = (result as NetworkResult.Success).data.orEmpty()
            assertEquals(listOf("Disney Plus", "Amazon Video"), platforms.map { it.name })
        }
    }

    @Test
    fun `GIVEN an empty response WHEN getPlatforms THEN it returns an empty list`() {
        testCoroutineRule.runBlockingTest {
            // GIVEN
            whenever(mockApi.getWatchProviders(MOVIE_ID)).doReturn(mockPlatformsModel.copy(results = emptyMap()))

            // WHEN
            val result = sutDataSourceRemote.getPlatforms(MOVIE_ID)

            // THEN
            assertTrue(result is NetworkResult.Success)
            assertTrue((result as NetworkResult.Success).data.orEmpty().isEmpty())
        }
    }

    @Test
    fun `GIVEN an api error WHEN getPlatforms THEN it returns NetworkResult Error`() {
        testCoroutineRule.runBlockingTest {
            // GIVEN
            whenever(mockApi.getWatchProviders(MOVIE_ID)).doThrow(RuntimeException("network"))

            // WHEN
            val result = sutDataSourceRemote.getPlatforms(MOVIE_ID)

            // THEN
            assertTrue(result is NetworkResult.Error)
            assertEquals("network", (result as NetworkResult.Error).message)
        }
    }
}
