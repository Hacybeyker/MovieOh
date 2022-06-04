package com.hacybeyker.movieoh.domain.usecase

import com.hacybeyker.movieoh.domain.entity.MovieEntity
import com.hacybeyker.movieoh.domain.repository.UpcomingRepository
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
class UpcomingUseCaseTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val mockUpcomingRepository: UpcomingRepository = mock()

    private val mockListMovieEntity: List<MovieEntity> = mock()

    private val sutUpcomingUseCase: UpcomingUseCase by lazy { UpcomingUseCase(mockUpcomingRepository) }

    @Test
    fun `when call fetchTrendingMovie then repository call fetchTrendingMovie`() {
        testCoroutineRule.runBlockingTest {
            whenever(mockUpcomingRepository.fetchUpcoming(anyInt())).doReturn(mockListMovieEntity)
            whenever(mockListMovieEntity.size).doReturn(2)

            val resultData = sutUpcomingUseCase.fetchUpcoming(anyInt())

            assertNotNull(sutUpcomingUseCase)
            assertNotNull(resultData)
            assertEquals(2, mockListMovieEntity.size)
            verify(mockUpcomingRepository, times(numInvocations = 1)).fetchUpcoming(anyInt())
        }
    }
}
