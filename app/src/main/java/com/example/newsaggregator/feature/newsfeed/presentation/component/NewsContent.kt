package com.example.newsaggregator.feature.newsfeed.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.newsaggregator.feature.newsfeed.domain.model.NewsItem

@Composable
fun NewsContent(
    newsItem: NewsItem,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        NewsTitle(newsItem.title)

        Spacer(modifier = Modifier.height(8.dp))

        NewsMetadata(author = newsItem.author)

        newsItem.description?.let { description ->
            Spacer(modifier = Modifier.height(4.dp))
            NewsDescription(description)
        }

        Spacer(modifier = Modifier.height(8.dp))

        NewsFooter(
            source = newsItem.source,
            publishedAt = newsItem.publishedAt,
            isFavorite = newsItem.isFavorite,
            onFavoriteClick = onFavoriteClick
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NewsContentPreview() {
    MaterialTheme {
        NewsContent(
            newsItem = NewsItem(
                title = "Sample News Title",
                author = "John Doe",
                description = "This is a sample news description",
                source = "Sample Source",
                publishedAt = "2023-05-15",
                isFavorite = false,
                id = 0,
                url = "",
                imageUrl = null,
                content = "Sample News Content",
                category = null,
            ),
            onFavoriteClick = {}
        )
    }
}