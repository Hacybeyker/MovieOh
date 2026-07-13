package com.hacybeyker.uikit.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hacybeyker.uikit.R
import com.hacybeyker.uikit.theme.MovieOhTheme

@Composable
fun SectionHeader(
    title: String,
    modifier: Modifier = Modifier,
    showIcon: Boolean = true,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(15.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.weight(1f),
        )
        if (showIcon) {
            Icon(
                painter = painterResource(id = R.drawable.icon_arrow_right),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SectionHeaderPreview() {
    MovieOhTheme {
        SectionHeader(title = "Similar Movies")
    }
}
