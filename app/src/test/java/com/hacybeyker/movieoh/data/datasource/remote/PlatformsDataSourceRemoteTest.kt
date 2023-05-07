package com.hacybeyker.movieoh.data.datasource.remote

import com.hacybeyker.movieoh.commons.exception.ApiException
import com.hacybeyker.movieoh.data.api.PlatformsApi
import com.hacybeyker.movieoh.utils.TestCoroutineRule
import com.hacybeyker.movieoh.utils.mockPlatformsModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class PlatformsDataSourceRemoteTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val mockApi: PlatformsApi = mock()

    private lateinit var sutDataSourceRemote: PlatformsDataSourceRemote

    @Before
    fun setup() {
        sutDataSourceRemote = PlatformsDataSourceRemote(mockApi)
    }

    @Test
    fun verifySuccess() {
        testCoroutineRule.runBlockingTest {
            // GIVEN
            val mockTitle = anyString()
            whenever(mockApi.getPlatforms(mockTitle)).doReturn(mockPlatformsModel)

            // WHEN
            val result = sutDataSourceRemote.getPlatforms(mockTitle)

            // THEN
            assertNotNull(sutDataSourceRemote)
            assertNotNull(result)
            verify(mockApi).getPlatforms(mockTitle)
        }
    }

    @Test
    fun verifySuccessEmpty() {
        testCoroutineRule.runBlockingTest {
            // GIVEN
            val mockTitle = anyString()
            whenever(mockApi.getPlatforms(mockTitle)).doReturn(mockPlatformsModel.copy(results = emptyList()))

            // WHEN
            val result = sutDataSourceRemote.getPlatforms(mockTitle)

            // THEN
            assertNotNull(sutDataSourceRemote)
            assertNotNull(result)
            verify(mockApi).getPlatforms(mockTitle)
        }
    }

    @Test
    fun verifyError() {
        testCoroutineRule.runBlockingTest {
            val mockTitle = anyString()
            try {
                whenever(mockApi.getPlatforms(mockTitle)).doThrow(RuntimeException(ApiException()))

                sutDataSourceRemote.getPlatforms(mockTitle)
            } catch (e: Exception) {
                (e.cause as? ApiException).let {
                    Assert.assertEquals(e.cause, it)
                    verify(mockApi).getPlatforms(mockTitle)
                }
            }
        }
    }
}
