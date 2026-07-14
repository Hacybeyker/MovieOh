package com.hacybeyker.movieoh.domain.repository

import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    fun observeFavorites(): Flow<Set<Int>>

    suspend fun toggleFavorite(movieId: Int)
}
