package com.hacybeyker.uikit.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hacybeyker.uikit.theme.MovieOhGradients

private val BUTTON_CORNER_RADIUS = 24.dp

@Composable
fun GradientButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        color = Color.White,
        textAlign = TextAlign.Center,
        modifier =
            modifier
                .clip(RoundedCornerShape(BUTTON_CORNER_RADIUS))
                .background(MovieOhGradients.accent)
                .clickable(onClick = onClick)
                .padding(horizontal = 24.dp, vertical = 12.dp),
    )
}
