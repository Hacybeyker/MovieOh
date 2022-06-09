package com.hacybeyker.movieoh.data.datasource.remote

import com.hacybeyker.movieoh.commons.exception.ApiException
import com.hacybeyker.movieoh.data.api.CreditsApi
import com.hacybeyker.movieoh.data.model.remote.response.CreditsResponseModel
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
class CreditsDataSourceRemoteTest {

    @get:Rule
    val testCoroutine: TestCoroutineRule = TestCoroutineRule()

    private val mockCreditApi: CreditsApi = mock()

    private val mockCreditsResponseModel: CreditsResponseModel = mock()

    private lateinit var sutCreditsDataSourceRemote: CreditsDataSourceRemote

    @Before
    fun setup() {
        sutCreditsDataSourceRemote = CreditsDataSourceRemote(mockCreditApi)
    }

    @Test
    fun validateSuccess() {
        testCoroutine.runBlockingTest {
            whenever(mockCreditApi.fetchCredits(anyInt())).doReturn(mockCreditsResponseModel)

            val resultData = sutCreditsDataSourceRemote.fetchCredits(anyInt())

            assertNotNull(sutCreditsDataSourceRemote)
            assertNotNull(resultData)
            verify(mockCreditApi, times(1)).fetchCredits(anyInt())
        }
    }

    @Test
    fun validateFail() {
        testCoroutine.runBlockingTest {
            try {
                whenever(mockCreditApi.fetchCredits(anyInt())).doThrow(ApiException())
            } catch (e: Exception) {
                (e.cause as? ApiException)?.let {
                    assertEquals(e.cause, it)
                    verify(mockCreditApi, times(1)).fetchCredits(anyInt())
                }
            }
        }
    }
}
