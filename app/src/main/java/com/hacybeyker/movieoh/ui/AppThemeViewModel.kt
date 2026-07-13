package com.hacybeyker.movieoh.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hacybeyker.movieoh.domain.entity.ThemeMode
import com.hacybeyker.movieoh.domain.usecase.ThemeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

private const val SUBSCRIPTION_TIMEOUT_MILLIS = 5_000L

@HiltViewModel
class AppThemeViewModel
    @Inject
    constructor(
        themeUseCase: ThemeUseCase,
    ) : ViewModel() {
        val themeMode: StateFlow<ThemeMode> =
            themeUseCase
                .observeThemeMode()
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(SUBSCRIPTION_TIMEOUT_MILLIS),
                    initialValue = ThemeMode.SYSTEM,
                )
    }
