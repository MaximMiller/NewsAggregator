package com.example.newsaggregator.feature.newsfeed.presentation.screen.feed


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.newsaggregator.feature.newsfeed.domain.model.NewsItem
import com.example.newsaggregator.feature.newsfeed.presentation.component.NewsCardCell
import com.example.newsaggregator.feature.newsfeed.presentation.screen.feed.state.NewsFeedState
import com.example.newsaggregator.feature.newsfeed.presentation.viewmodel.NewsFeedViewModel

@Composable
fun NewsFeedScreen(
    viewModel: NewsFeedViewModel = hiltViewModel(),
    onNewsClick: (String) -> Unit = {}
) {
    val state by viewModel.state.collectAsState()

    NewsFeedContent(
        state = state,
        onRefresh = { viewModel.loadHeadlines() },
        onNewsClick = onNewsClick,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NewsFeedContent(
    state: NewsFeedState,
    onRefresh: () -> Unit,
    onNewsClick: (String) -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(title = { Text("Новости") })

        when {
            state.isLoading -> LoadingView()
            state.error != null -> ErrorView(state.error, onRefresh)
            else -> NewsList(
                news = state.newsItems,
                onNewsClick = onNewsClick
            )
        }
    }
}

@Composable
private fun NewsList(
    news: List<NewsItem>,
    onNewsClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    if (news.isEmpty()) {
        EmptyStateView()
        return
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            count = news.size,
            key = { index -> news[index].id }
        ) { index ->
            val item = news[index]
            NewsCardCell(
                newsItem = item,
                onClick = { onNewsClick(item.id) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun EmptyStateView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Новости не найдены", style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
private fun LoadingView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorView(error: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = error, color = MaterialTheme.colorScheme.error)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Text("Повторить")
        }
    }
}