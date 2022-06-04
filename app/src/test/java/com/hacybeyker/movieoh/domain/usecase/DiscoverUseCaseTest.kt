package com.hacybeyker.movieoh.domain.usecase

import com.hacybeyker.movieoh.domain.entity.MovieEntity
import com.hacybeyker.movieoh.domain.repository.DiscoverRepository
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
class DiscoverUseCaseTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val mockDiscoverRepository: DiscoverRepository = mock()

    private val mockListMovieEntity: List<MovieEntity> = mock()

    private val sutDiscoverUseCase: DiscoverUseCase by lazy { DiscoverUseCase(mockDiscoverRepository) }

    @Test
    fun `when call fetchTrendingMovie then repository call fetchTrendingMovie`() {
        testCoroutineRule.runBlockingTest {
            whenever(mockDiscoverRepository.fetchDiscover(anyInt(), anyInt()))
                .doReturn(mockListMovieEntity)

            val resultData = sutDiscoverUseCase.fetchDiscover(anyInt(), anyInt())

            assertNotNull(sutDiscoverUseCase)
            assertNotNull(resultData)
            verify(mockDiscoverRepository, times(numInvocations = 1))
                .fetchDiscover(anyInt(), anyInt())
        }
    }
}
