package com.hacybeyker.movieoh.ui.movieactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hacybeyker.movieoh.domain.usecase.FavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val SUBSCRIPTION_TIMEOUT_MILLIS = 5_000L

@HiltViewModel
class MovieActionsViewModel
    @Inject
    constructor(
        private val favoriteUseCase: FavoriteUseCase,
    ) : ViewModel() {
        val favorites: StateFlow<Set<Int>> =
            favoriteUseCase
                .observeFavorites()
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(SUBSCRIPTION_TIMEOUT_MILLIS),
                    initialValue = emptySet(),
                )

        fun toggleFavorite(movieId: Int) {
            viewModelScope.launch {
                favoriteUseCase.toggleFavorite(movieId)
            }
        }
    }
