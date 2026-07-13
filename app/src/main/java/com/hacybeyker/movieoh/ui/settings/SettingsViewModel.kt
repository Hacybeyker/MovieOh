package com.hacybeyker.movieoh.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hacybeyker.movieoh.di.module.CoroutineModule.Companion.DISPATCHER_IO
import com.hacybeyker.movieoh.domain.entity.ThemeMode
import com.hacybeyker.movieoh.domain.usecase.ThemeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class SettingsViewModel
    @Inject
    constructor(
        private val themeUseCase: ThemeUseCase,
        @param:Named(DISPATCHER_IO) private val dispatcherIO: CoroutineDispatcher,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(SettingsUiState())
        val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

        init {
            viewModelScope.launch(dispatcherIO) {
                themeUseCase.observeThemeMode().collect { mode ->
                    _uiState.update { it.copy(themeMode = mode) }
                }
            }
        }

        fun onThemeSelected(mode: ThemeMode) {
            viewModelScope.launch(dispatcherIO) {
                themeUseCase.setThemeMode(mode)
            }
        }
    }
