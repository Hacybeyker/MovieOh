package com.hacybeyker.movieoh.domain.usecase

import com.hacybeyker.movieoh.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoriteUseCase
    @Inject
    constructor(
        private val favoriteRepository: FavoriteRepository,
    ) {
        fun observeFavorites(): Flow<Set<Int>> = favoriteRepository.observeFavorites()

        suspend fun toggleFavorite(movieId: Int) = favoriteRepository.toggleFavorite(movieId)
    }
