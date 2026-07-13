package com.hacybeyker.movieoh.ui.settings

import android.content.Context
import android.content.Intent
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hacybeyker.movieoh.BuildConfig
import com.hacybeyker.movieoh.R
import com.hacybeyker.movieoh.domain.entity.ThemeMode
import com.hacybeyker.uikit.component.SectionHeader

private const val TMDB_URL = "https://www.themoviedb.org/"

@Composable
fun SettingsScreen(viewModel: SettingsViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    SettingsContent(
        uiState = uiState,
        onThemeSelected = viewModel::onThemeSelected,
    )
}

@Composable
fun SettingsContent(
    uiState: SettingsUiState,
    onThemeSelected: (ThemeMode) -> Unit,
) {
    val context = LocalContext.current
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .safeDrawingPadding()
                .verticalScroll(rememberScrollState()),
    ) {
        SectionHeader(title = stringResource(id = R.string.nav_settings))
        ThemeSection(themeMode = uiState.themeMode, onThemeSelected = onThemeSelected)
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 10.dp),
            color = MaterialTheme.colorScheme.surfaceVariant,
        )
        AboutSection(context = context)
    }
}

@Composable
private fun ThemeSection(
    themeMode: ThemeMode,
    onThemeSelected: (ThemeMode) -> Unit,
) {
    Column(modifier = Modifier.padding(horizontal = 15.dp)) {
        ThemeOption(R.string.theme_light, ThemeMode.LIGHT, themeMode, onThemeSelected)
        ThemeOption(R.string.theme_dark, ThemeMode.DARK, themeMode, onThemeSelected)
        ThemeOption(R.string.theme_system, ThemeMode.SYSTEM, themeMode, onThemeSelected)
    }
}

@Composable
private fun ThemeOption(
    @StringRes labelRes: Int,
    mode: ThemeMode,
    selectedMode: ThemeMode,
    onThemeSelected: (ThemeMode) -> Unit,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .clickable { onThemeSelected(mode) }
                .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RadioButton(selected = mode == selectedMode, onClick = { onThemeSelected(mode) })
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = stringResource(id = labelRes),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
        )
    }
}

@Composable
private fun AboutSection(context: Context) {
    Column(modifier = Modifier.padding(15.dp)) {
        Text(
            text = stringResource(id = R.string.app_version, BuildConfig.VERSION_NAME),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(id = R.string.tmdb_attribution),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier =
                Modifier.clickable {
                    context.startActivity(Intent(Intent.ACTION_VIEW, TMDB_URL.toUri()))
                },
        )
    }
}
