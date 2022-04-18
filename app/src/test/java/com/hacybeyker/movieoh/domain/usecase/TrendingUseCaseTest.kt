package com.hacybeyker.movieoh.domain.usecase

import com.hacybeyker.movieoh.domain.entity.MovieEntity
import com.hacybeyker.movieoh.domain.repository.TrendingRepository
import com.hacybeyker.movieoh.utils.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
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
class TrendingUseCaseTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val mockRepository: TrendingRepository = mock()

    private val mockListMovieEntity: List<MovieEntity> = mock()

    private val sutTrendingUseCase: TrendingUseCase by lazy { TrendingUseCase(mockRepository) }

    @Test
    fun `when call fetchTrendingMovie then repository call fetchTrendingMovie`() {
        testCoroutineRule.runBlockingTest {
            whenever(mockRepository.fetchTrendingMovie(anyInt())).doReturn(mockListMovieEntity)
            whenever(mockListMovieEntity.size).doReturn(2)

            val result = sutTrendingUseCase.fetchTrendingMovie(anyInt())

            assertNotNull(sutTrendingUseCase)
            assertNotNull(result)
            assertEquals(2, mockListMovieEntity.size)
            verify(mockRepository, times(numInvocations = 1)).fetchTrendingMovie(anyInt())
        }
    }
}
