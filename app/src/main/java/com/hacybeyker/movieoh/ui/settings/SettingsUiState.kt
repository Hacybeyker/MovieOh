package com.hacybeyker.movieoh.ui.settings

import com.hacybeyker.movieoh.domain.entity.ThemeMode

data class SettingsUiState(
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
)
