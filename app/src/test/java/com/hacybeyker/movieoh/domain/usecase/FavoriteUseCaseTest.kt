package com.hacybeyker.movieoh.domain.usecase

import com.hacybeyker.movieoh.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class FavoriteUseCaseTest {
    private val mockRepository: FavoriteRepository = mock()
    private val useCase = FavoriteUseCase(mockRepository)

    @Test
    fun `GIVEN repository emits favorites WHEN observeFavorites THEN they are exposed as is`() =
        runBlocking {
            whenever(mockRepository.observeFavorites()).thenReturn(flowOf(setOf(1, 2)))

            assertEquals(setOf(1, 2), useCase.observeFavorites().first())
        }

    @Test
    fun `WHEN toggleFavorite THEN it delegates to the repository`() =
        runBlocking {
            useCase.toggleFavorite(7)

            verify(mockRepository).toggleFavorite(7)
        }
}
