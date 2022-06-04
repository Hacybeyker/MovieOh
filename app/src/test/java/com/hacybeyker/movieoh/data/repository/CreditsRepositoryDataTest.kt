package com.hacybeyker.movieoh.data.repository

import com.hacybeyker.movieoh.data.datasource.CreditsDataSource
import com.hacybeyker.movieoh.domain.entity.CreditsEntity
import com.hacybeyker.movieoh.utils.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
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
class CreditsRepositoryDataTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val mockCreditsDataSource: CreditsDataSource = mock()

    private val mockCreditsEntity: CreditsEntity = mock()

    private lateinit var sutCreditsRepositoryData: CreditsRepositoryData

    @Before
    fun setup() {
        sutCreditsRepositoryData = CreditsRepositoryData(mockCreditsDataSource)
    }

    @Test
    fun `when call fetchCredits then datasource call fetchCredits`() {
        testCoroutineRule.runBlockingTest {
            whenever(mockCreditsDataSource.fetchCredits(anyInt())).doReturn(mockCreditsEntity)

            val resultData = sutCreditsRepositoryData.fetchCredits(anyInt())

            Assert.assertNotNull(sutCreditsRepositoryData)
            Assert.assertNotNull(resultData)
            verify(mockCreditsDataSource, times(1)).fetchCredits(anyInt())
        }
    }
}
