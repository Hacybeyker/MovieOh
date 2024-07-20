package com.hacybeyker.movieoh.domain.usecase

import com.hacybeyker.movieoh.commons.base.NetworkResult
import com.hacybeyker.movieoh.domain.repository.PlatformsRepository
import com.hacybeyker.movieoh.utils.TestCoroutineRule
import com.hacybeyker.movieoh.utils.mockPlatformsEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class PlatformsUseCaseTest {
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val mockRepository: PlatformsRepository = mock()

    private lateinit var sutUseCase: PlatformsUseCase

    @Before
    fun setup() {
        sutUseCase = PlatformsUseCase(mockRepository)
    }

    @Test
    fun `WHEN execute useCase THEN verify is success`() {
        testCoroutineRule.runBlockingTest {
            // WHEN
            val mockTitle = anyString()
            whenever(mockRepository.getPlatforms(mockTitle)).doReturn(
                NetworkResult.Success(
                    mockPlatformsEntity,
                ),
            )

            val resultData = sutUseCase.getPlatforms(mockTitle)

            // THEN
            assertNotNull(sutUseCase)
            assertNotNull(resultData)
            verify(mockRepository).getPlatforms(mockTitle)
        }
    }
}
