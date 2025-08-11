package com.example.newsaggregator.feature.newsfeed.presentation.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun NewsDescription(
    description: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = description,
        modifier = modifier,
        style = MaterialTheme.typography.bodyMedium,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
    )
}

@Preview(showBackground = true)
@Composable
fun NewsDescriptionPreview() {
    MaterialTheme {
        NewsDescription(
            description = "This is a sample news description that might be a bit longer to test the ellipsis functionality"
        )
    }
}