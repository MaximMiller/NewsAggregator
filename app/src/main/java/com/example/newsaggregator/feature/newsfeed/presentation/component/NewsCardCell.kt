package com.example.newsaggregator.feature.newsfeed.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.newsaggregator.R
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
            if (newsItem.imageUrl.isNullOrEmpty()) {
                Image(
                    painter = painterResource(R.drawable.ic_news_aggregator_playstore),
                    contentDescription = contentDescription,
                    modifier = modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )
            } else {
                AsyncImage(
                    model = newsItem.imageUrl,
                    contentDescription = contentDescription,
                    modifier = modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(R.drawable.ic_news_aggregator_playstore),
                    error = painterResource(R.drawable.ic_news_aggregator_playstore)
                )
            }
        }

        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = newsItem.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            newsItem.author?.let { author ->
                Text(
                    text = author,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
                Spacer(modifier = Modifier.height(4.dp))
            }

            newsItem.description?.let { description ->
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(
                        text = newsItem.source ?: "Unknown source",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                    Text(
                        text =newsItem.publishedAt,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }

                IconButton(
                    onClick = { onFavoriteClick(!newsItem.isFavorite) },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = if (newsItem.isFavorite)
                            Icons.Default.Favorite
                        else
                            Icons.Default.FavoriteBorder,
                        tint = if (newsItem.isFavorite)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        contentDescription = "Favorite",
                    )
                }
            }
        }
    }
}