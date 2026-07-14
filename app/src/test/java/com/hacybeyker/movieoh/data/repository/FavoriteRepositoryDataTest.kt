package com.hacybeyker.movieoh.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.File

class FavoriteRepositoryDataTest {
    private lateinit var tempFile: File
    private lateinit var dataStore: DataStore<Preferences>
    private lateinit var repository: FavoriteRepositoryData

    @Before
    fun setUp() {
        tempFile = File.createTempFile("favorite_test", ".preferences_pb")
        dataStore =
            PreferenceDataStoreFactory.create(
                scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
                produceFile = { tempFile },
            )
        repository = FavoriteRepositoryData(dataStore)
    }

    @After
    fun tearDown() {
        tempFile.delete()
    }

    @Test
    fun `GIVEN no favorites WHEN observing THEN emits an empty set`() =
        runBlocking {
            assertTrue(repository.observeFavorites().first().isEmpty())
        }

    @Test
    fun `GIVEN a movie is toggled WHEN observing THEN it appears as favorite`() =
        runBlocking {
            repository.toggleFavorite(42)

            assertEquals(setOf(42), repository.observeFavorites().first())
        }

    @Test
    fun `GIVEN a favorite movie WHEN toggled again THEN it is removed`() =
        runBlocking {
            repository.toggleFavorite(42)
            repository.toggleFavorite(42)

            assertTrue(repository.observeFavorites().first().isEmpty())
        }
}
