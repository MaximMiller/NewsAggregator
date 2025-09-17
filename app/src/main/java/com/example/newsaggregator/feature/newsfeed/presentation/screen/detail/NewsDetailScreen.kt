package com.example.newsaggregator.feature.newsfeed.presentation.screen.detail

import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.newsaggregator.feature.newsfeed.domain.model.NewsItem
import com.example.newsaggregator.feature.newsfeed.presentation.screen.detail.state.NewsDetailState
import com.example.newsaggregator.feature.newsfeed.presentation.viewmodel.NewsDetailViewModel
import java.text.SimpleDateFormat
import java.util.Locale
import androidx.core.net.toUri
import com.example.newsaggregator.feature.newsfeed.presentation.component.ErrorView
import com.example.newsaggregator.feature.newsfeed.presentation.component.LoadingView
import com.example.newsaggregator.feature.newsfeed.presentation.component.NewsImage

@Composable
fun NewsDetailScreen(
    newsId: Long,
    viewModel: NewsDetailViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(newsId) {
        viewModel.loadNewsDetail(newsId)
    }

    NewsDetailContent(
        state = state,
        onBackClick = onBackClick,
        onRetry = { viewModel.loadNewsDetail(newsId) }
    )
}

@Composable
fun NewsDetailContent(
    state: NewsDetailState,
    onBackClick: () -> Unit,
    onRetry: () -> Unit
) {
    Scaffold(
        topBar = {
            NewsDetailTopAppBar(
                onBackClick = onBackClick,
                category = state.newsItem?.category
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when {
                state.isLoading -> LoadingView()
                state.error != null -> ErrorView(
                    state.error,
                    onRetry = onRetry
                )

                state.newsItem != null -> NewsDetailView(newsItem = state.newsItem)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsDetailTopAppBar(
    onBackClick: () -> Unit,
    category: String?
) {
    TopAppBar(
        title = {
            if (category != null) {
                Text(
                    text = "Детали новости: ${category.replaceFirstChar { it.titlecase() }}",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            } else {
                Text(
                    text = "Детали новости в общей категории",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Назад"
                )
            }
        }
    )
}

@Composable
fun NewsDetailView(newsItem: NewsItem) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        Text(
            text = newsItem.title,
            style = typography.headlineMedium,
            color = colorScheme.onSurface,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            lineHeight = 32.sp
        )
        NewsImage(
            imageUrl = newsItem.imageUrl,
            contentDescription = newsItem.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(horizontal = 16.dp)
        )

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = "Источник: ${newsItem.source}",
                style = typography.bodySmall,
                color = colorScheme.onSurface.copy(alpha = 0.7f)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Опубликовано: ${formatDate(newsItem.publishedAt)}",
                style = typography.bodySmall,
                color = colorScheme.onSurface.copy(alpha = 0.7f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            newsItem.description?.let { description ->
                Text(
                    text = description,
                    style = typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            newsItem.content?.let { content ->
                Text(
                    text = content,
                    style = typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, newsItem.url.toUri())
                    context.startActivity(intent)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Читать на сайте")
            }
            Spacer(modifier = Modifier.height(24.dp))

            AdPlaceholder()
        }
    }
}

@Composable
fun AdPlaceholder() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(
            containerColor = colorScheme.surfaceVariant.copy(alpha = 0.3f)
        ),
        border = BorderStroke(1.dp, colorScheme.outline.copy(alpha = 0.3f))
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Реклама",
                    tint = colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = "Рекламный баннер",
                    style = typography.labelSmall,
                    color = colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                )
            }

            Box(
                modifier = Modifier.align(Alignment.Center)
            ) {
                Text(
                    text = "Здесь может быть ваша реклама",
                    style = typography.bodySmall,
                    color = colorScheme.onSurfaceVariant.copy(alpha = 0.4f)
                )
            }
        }
    }
}

fun formatDate(dateString: String): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd MMMM yyyy, HH:mm", Locale.getDefault())
        val date = inputFormat.parse(dateString)
        outputFormat.format(date ?: dateString)
    } catch (e: Exception) {
        dateString
    }
}