package com.hacybeyker.movieoh.data.datasource.remote

import com.hacybeyker.movieoh.commons.exception.ApiException
import com.hacybeyker.movieoh.data.api.UpcomingApi
import com.hacybeyker.movieoh.data.model.remote.response.ResultResponseModel
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
class UpcomingDataSourceRemoteTest {
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val mockUpcomingApi: UpcomingApi = mock()

    private val mockResultResponseModel: ResultResponseModel = mock()

    private lateinit var sutUpcomingDataSourceRemote: UpcomingDataSourceRemote

    @Before
    fun setup() {
        sutUpcomingDataSourceRemote = UpcomingDataSourceRemote(mockUpcomingApi)
    }

    @Test
    fun verifyApiSuccess() {
        testCoroutineRule.runBlockingTest {
            whenever(mockUpcomingApi.fetchUpcoming(anyInt())).doReturn(mockResultResponseModel)

            val resultData = sutUpcomingDataSourceRemote.fetchUpcoming(anyInt())

            Assert.assertNotNull(sutUpcomingDataSourceRemote)
            Assert.assertNotNull(resultData)
            verify(mockUpcomingApi, times(1)).fetchUpcoming(anyInt())
        }
    }

    @Test
    fun verifyApiError() {
        testCoroutineRule.runBlockingTest {
            try {
                whenever(mockUpcomingApi.fetchUpcoming(anyInt()))
                    .doThrow(RuntimeException(ApiException()))
                sutUpcomingDataSourceRemote.fetchUpcoming(anyInt())
            } catch (e: Exception) {
                (e.cause as? ApiException).let {
                    Assert.assertEquals(e.cause, it)
                    verify(mockUpcomingApi, times(1)).fetchUpcoming(anyInt())
                }
            }
        }
    }
}
