package com.hacybeyker.movieoh.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import com.hacybeyker.movieoh.domain.entity.ThemeMode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.File

class ThemeRepositoryDataTest {
    private lateinit var tempFile: File
    private lateinit var dataStore: DataStore<Preferences>
    private lateinit var repository: ThemeRepositoryData

    @Before
    fun setUp() {
        tempFile = File.createTempFile("theme_test", ".preferences_pb")
        dataStore =
            PreferenceDataStoreFactory.create(
                scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
                produceFile = { tempFile },
            )
        repository = ThemeRepositoryData(dataStore)
    }

    @After
    fun tearDown() {
        tempFile.delete()
    }

    @Test
    fun `GIVEN no stored preference WHEN observing THEN defaults to SYSTEM`() =
        runBlocking {
            assertEquals(ThemeMode.SYSTEM, repository.observeThemeMode().first())
        }

    @Test
    fun `GIVEN a mode is set WHEN observing THEN it is returned`() =
        runBlocking {
            repository.setThemeMode(ThemeMode.DARK)

            assertEquals(ThemeMode.DARK, repository.observeThemeMode().first())
        }
}
