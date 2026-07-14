package com.hacybeyker.movieoh.domain.repository

import com.hacybeyker.movieoh.domain.entity.ThemeMode
import kotlinx.coroutines.flow.Flow

interface ThemeRepository {
    fun observeThemeMode(): Flow<ThemeMode>

    suspend fun setThemeMode(mode: ThemeMode)
}
