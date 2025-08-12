package com.example.newsaggregator.feature.newsfeed.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.newsaggregator.R

@Composable
fun NewsImage(
    imageUrl: String?,
    contentDescription: String?,
    modifier: Modifier = Modifier
) {
    if (imageUrl.isNullOrEmpty()) {
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
            model = imageUrl,
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

@Preview
@Composable
fun NewsImagePreview() {
    MaterialTheme {
        NewsImage(
            imageUrl = null,
            contentDescription = "Sample image",
            modifier = Modifier.fillMaxWidth()
        )
    }
}