package com.hacybeyker.movieoh.domain.usecase

import com.hacybeyker.movieoh.domain.entity.MovieEntity
import com.hacybeyker.movieoh.domain.repository.SimilarRepository
import com.hacybeyker.movieoh.utils.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class SimilarUseCaseTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val mockSimilarRepository: SimilarRepository = mock()

    private val mockListMovieEntity: List<MovieEntity> = mock()

    private val sutSimilarUseCase: SimilarUseCase by lazy { SimilarUseCase(mockSimilarRepository) }

    @Test
    fun `when call fetchTrendingMovie then repository call fetchTrendingMovie`() {
        testCoroutineRule.runBlockingTest {
            whenever(mockSimilarRepository.fetchSimilar(anyInt())).doReturn(mockListMovieEntity)

            val resultData = sutSimilarUseCase.fetchSimilar(anyInt())

            assertNotNull(sutSimilarUseCase)
            assertNotNull(resultData)
            verify(mockSimilarRepository, times(numInvocations = 1)).fetchSimilar(anyInt())
        }
    }
}
