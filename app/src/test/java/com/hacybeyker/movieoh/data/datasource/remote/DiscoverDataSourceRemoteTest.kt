package com.hacybeyker.movieoh.data.datasource.remote

import com.hacybeyker.movieoh.commons.exception.ApiException
import com.hacybeyker.movieoh.data.api.DiscoverApi
import com.hacybeyker.movieoh.data.model.remote.response.ResultResponseModel
import com.hacybeyker.movieoh.utils.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
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
class DiscoverDataSourceRemoteTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val mockDiscoverApi: DiscoverApi = mock()

    private val mockResultResponseModel: ResultResponseModel = mock()

    private lateinit var sutDiscoverDataSourceRemote: DiscoverDataSourceRemote

    @Before
    fun setup() {
        sutDiscoverDataSourceRemote = DiscoverDataSourceRemote(mockDiscoverApi)
    }

    @Test
    fun verifyApiSuccess() {
        testCoroutineRule.runBlockingTest {
            whenever(
                mockDiscoverApi.fetchDiscover(anyInt(), anyInt())
            ).doReturn(mockResultResponseModel)

            val resultData = sutDiscoverDataSourceRemote.fetchDiscover(anyInt(), anyInt())

            assertNotNull(sutDiscoverDataSourceRemote)
            assertNotNull(resultData)
            verify(mockDiscoverApi, times(1)).fetchDiscover(anyInt(), anyInt())
        }
    }

    @Test
    fun verifyApiError() {
        testCoroutineRule.runBlockingTest {
            try {
                whenever(
                    mockDiscoverApi.fetchDiscover(anyInt(), anyInt())
                ).doThrow(RuntimeException(ApiException()))

                sutDiscoverDataSourceRemote.fetchDiscover(anyInt(), anyInt())
            } catch (e: Exception) {
                (e.cause as? ApiException).let {
                    assertEquals(it, e.cause)
                    verify(mockDiscoverApi, times(1)).fetchDiscover(anyInt(), anyInt())
                }
            }
        }
    }
}
