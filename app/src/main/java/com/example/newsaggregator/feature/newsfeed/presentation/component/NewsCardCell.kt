package com.example.newsaggregator.feature.newsfeed.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.newsaggregator.feature.newsfeed.domain.model.NewsItem

@Composable
fun NewsCardCell(
    newsItem: NewsItem,
    modifier: Modifier = Modifier,
    onFavoriteClick: (Boolean) -> Unit = {},
    onClick: () -> Unit = {},
    contentDescription: String? = null
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column {
            NewsImage(
                imageUrl = newsItem.imageUrl,
                contentDescription = contentDescription
            )

            NewsContent(
                newsItem = newsItem,
                onFavoriteClick = onFavoriteClick,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewsCardCellPreview() {
    MaterialTheme {
        NewsCardCell(
            newsItem = NewsItem(
                title = "Sample News Title",
                author = "John Doe",
                description = "This is a sample news description that might be a bit longer to test the ellipsis functionality",
                source = "Sample Source",
                publishedAt = "2023-05-15",
                isFavorite = false,
                id = "",
                url = "",
                imageUrl = null,
                content = null,
                category = null,
            ),
            modifier = Modifier.padding(16.dp)
        )
    }
}