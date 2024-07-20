package com.hacybeyker.movieoh.data.datasource.remote

import com.hacybeyker.movieoh.commons.exception.ApiException
import com.hacybeyker.movieoh.data.api.SimilarApi
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
class SimilarDataSourceRemoteTest {
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val mockSimilarApi: SimilarApi = mock()

    private val mockResultResponseModel: ResultResponseModel = mock()

    private lateinit var sutSimilarDataSourceRemote: SimilarDataSourceRemote

    @Before
    fun setup() {
        sutSimilarDataSourceRemote = SimilarDataSourceRemote(mockSimilarApi)
    }

    @Test
    fun verifyApiSuccess() {
        testCoroutineRule.runBlockingTest {
            whenever(mockSimilarApi.fetchSimilar(anyInt())).doReturn(mockResultResponseModel)

            val resultData = sutSimilarDataSourceRemote.fetchSimilar(anyInt())

            assertNotNull(sutSimilarDataSourceRemote)
            assertNotNull(resultData)
            verify(mockSimilarApi, times(1)).fetchSimilar(anyInt())
        }
    }

    @Test
    fun verifyApiError() {
        testCoroutineRule.runBlockingTest {
            try {
                whenever(
                    mockSimilarApi.fetchSimilar(anyInt()),
                ).doThrow(RuntimeException(ApiException()))

                sutSimilarDataSourceRemote.fetchSimilar(anyInt())
            } catch (e: Exception) {
                (e.cause as? ApiException).let {
                    assertEquals(e.cause, it)
                    verify(mockSimilarApi, times(1)).fetchSimilar(anyInt())
                }
            }
        }
    }
}
