package com.hacybeyker.movieoh.data.repository

import com.hacybeyker.movieoh.data.datasource.SimilarDataSource
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
class SimilarRepositoryDataTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val mockSimilarDataSource: SimilarDataSource = mock()

    private val mockListMovieEntity: List<MovieEntity> = mock()

    private lateinit var sutSimilarRepositoryData: SimilarRepositoryData

    @Before
    fun setup() {
        sutSimilarRepositoryData = SimilarRepositoryData(mockSimilarDataSource)
    }

    @Test
    fun `when call fetchSimilar then datasource call fetchSimilar`() {
        testCoroutineRule.runBlockingTest {
            whenever(mockSimilarDataSource.fetchSimilar(anyInt())).doReturn(mockListMovieEntity)

            val resultData = sutSimilarRepositoryData.fetchSimilar(anyInt())

            assertNotNull(sutSimilarRepositoryData)
            assertNotNull(resultData)
            verify(mockSimilarDataSource, times(numInvocations = 1)).fetchSimilar(anyInt())
        }
    }
}
