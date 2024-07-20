package com.hacybeyker.movieoh.domain.usecase

import com.hacybeyker.movieoh.domain.entity.CreditsEntity
import com.hacybeyker.movieoh.domain.repository.CreditsRepository
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
class CreditsUseCaseTest {
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val mockCreditsRepository: CreditsRepository = mock()

    private val mockCreditsEntity: CreditsEntity = mock()

    private val sutCreditsUseCase: CreditsUseCase by lazy { CreditsUseCase(mockCreditsRepository) }

    @Test
    fun `when call fetchTrendingMovie then repository call fetchTrendingMovie`() {
        testCoroutineRule.runBlockingTest {
            whenever(mockCreditsRepository.fetchCredits(anyInt())).doReturn(mockCreditsEntity)

            val resultData = sutCreditsUseCase.fetchCredits(anyInt())

            assertNotNull(sutCreditsUseCase)
            assertNotNull(resultData)
            verify(mockCreditsRepository, times(numInvocations = 1)).fetchCredits(anyInt())
        }
    }
}
