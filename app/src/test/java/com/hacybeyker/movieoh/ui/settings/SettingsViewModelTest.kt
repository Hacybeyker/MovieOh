package com.hacybeyker.movieoh.ui.settings

import com.hacybeyker.movieoh.domain.entity.ThemeMode
import com.hacybeyker.movieoh.domain.usecase.ThemeUseCase
import com.hacybeyker.movieoh.utils.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class SettingsViewModelTest {
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val mockThemeUseCase: ThemeUseCase = mock()

    private fun buildViewModel(): SettingsViewModel =
        SettingsViewModel(
            themeUseCase = mockThemeUseCase,
            dispatcherIO = testCoroutineRule.testCoroutineDispatcher,
        )

    @Test
    fun `GIVEN repository emits a mode WHEN viewmodel is created THEN uiState exposes it`() {
        testCoroutineRule.runBlockingTest {
            // GIVEN
            whenever(mockThemeUseCase.observeThemeMode()).thenReturn(flowOf(ThemeMode.DARK))

            // WHEN
            val viewModel = buildViewModel()
            advanceUntilIdle()

            // THEN
            assertEquals(ThemeMode.DARK, viewModel.uiState.value.themeMode)
        }
    }

    @Test
    fun `WHEN onThemeSelected THEN it delegates to the use case`() {
        testCoroutineRule.runBlockingTest {
            // GIVEN
            whenever(mockThemeUseCase.observeThemeMode()).thenReturn(flowOf(ThemeMode.SYSTEM))
            val viewModel = buildViewModel()
            advanceUntilIdle()

            // WHEN
            viewModel.onThemeSelected(ThemeMode.LIGHT)
            advanceUntilIdle()

            // THEN
            verify(mockThemeUseCase).setThemeMode(ThemeMode.LIGHT)
        }
    }
}
