package com.hacybeyker.movieoh.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.hacybeyker.movieoh.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val FAVORITE_MOVIE_IDS = stringSetPreferencesKey("favorite_movie_ids")

class FavoriteRepositoryData
    @Inject
    constructor(
        private val dataStore: DataStore<Preferences>,
    ) : FavoriteRepository {
        override fun observeFavorites(): Flow<Set<Int>> =
            dataStore.data.map { preferences ->
                preferences[FAVORITE_MOVIE_IDS]?.mapNotNull { it.toIntOrNull() }?.toSet() ?: emptySet()
            }

        override suspend fun toggleFavorite(movieId: Int) {
            dataStore.edit { preferences ->
                val current = preferences[FAVORITE_MOVIE_IDS] ?: emptySet()
                val movieIdString = movieId.toString()
                preferences[FAVORITE_MOVIE_IDS] =
                    if (movieIdString in current) {
                        current - movieIdString
                    } else {
                        current + movieIdString
                    }
            }
        }
    }
