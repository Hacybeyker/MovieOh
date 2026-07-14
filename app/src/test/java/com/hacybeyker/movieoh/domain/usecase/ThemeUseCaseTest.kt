package com.hacybeyker.movieoh.domain.usecase

import com.hacybeyker.movieoh.domain.entity.ThemeMode
import com.hacybeyker.movieoh.domain.repository.ThemeRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class ThemeUseCaseTest {
    private val mockRepository: ThemeRepository = mock()
    private val useCase = ThemeUseCase(mockRepository)

    @Test
    fun `GIVEN repository emits a mode WHEN observeThemeMode THEN it is exposed as is`() =
        runBlocking {
            whenever(mockRepository.observeThemeMode()).thenReturn(flowOf(ThemeMode.DARK))

            assertEquals(ThemeMode.DARK, useCase.observeThemeMode().first())
        }

    @Test
    fun `WHEN setThemeMode THEN it delegates to the repository`() =
        runBlocking {
            useCase.setThemeMode(ThemeMode.LIGHT)

            verify(mockRepository).setThemeMode(ThemeMode.LIGHT)
        }
}
