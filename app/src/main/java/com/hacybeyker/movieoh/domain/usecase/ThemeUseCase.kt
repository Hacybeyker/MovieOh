package com.hacybeyker.movieoh.domain.usecase

import com.hacybeyker.movieoh.domain.entity.ThemeMode
import com.hacybeyker.movieoh.domain.repository.ThemeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ThemeUseCase
    @Inject
    constructor(
        private val themeRepository: ThemeRepository,
    ) {
        fun observeThemeMode(): Flow<ThemeMode> = themeRepository.observeThemeMode()

        suspend fun setThemeMode(mode: ThemeMode) = themeRepository.setThemeMode(mode)
    }
