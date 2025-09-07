package com.example.newsaggregator.feature.newsfeed.presentation.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun NewsMetadata(
    author: String?,
    modifier: Modifier = Modifier
) {
    author?.let {
        Text(
            text = it,
            modifier = modifier,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NewsMetadataPreview() {
    MaterialTheme {
        NewsMetadata(author = "John Doe")
    }
}