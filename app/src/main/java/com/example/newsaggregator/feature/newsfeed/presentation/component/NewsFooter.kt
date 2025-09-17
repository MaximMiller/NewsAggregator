package com.example.newsaggregator.feature.newsfeed.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun NewsFooter(
    source: String?,
    publishedAt: String,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth()
    ) {
        Column {
            Text(
                text = source ?: "Unknown source",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
            Text(
                text = publishedAt,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }

        FavoriteButton(
            isFavorite = isFavorite,
            onFavoriteClick = onFavoriteClick
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NewsFooterPreview() {
    MaterialTheme {
        NewsFooter(
            source = "Sample Source",
            publishedAt = "2023-05-15",
            isFavorite = true,
            onFavoriteClick = {}
        )
    }
}