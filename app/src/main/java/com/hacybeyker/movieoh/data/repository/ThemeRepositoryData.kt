package com.hacybeyker.movieoh.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.hacybeyker.movieoh.domain.entity.ThemeMode
import com.hacybeyker.movieoh.domain.repository.ThemeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val THEME_MODE = stringPreferencesKey("theme_mode")

class ThemeRepositoryData
    @Inject
    constructor(
        private val dataStore: DataStore<Preferences>,
    ) : ThemeRepository {
        override fun observeThemeMode(): Flow<ThemeMode> =
            dataStore.data.map { preferences ->
                preferences[THEME_MODE]?.let { runCatching { ThemeMode.valueOf(it) }.getOrNull() } ?: ThemeMode.SYSTEM
            }

        override suspend fun setThemeMode(mode: ThemeMode) {
            dataStore.edit { preferences ->
                preferences[THEME_MODE] = mode.name
            }
        }
    }
