package com.example.newsaggregator.feature.newsfeed.presentation.screen.feed


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.newsaggregator.core.util.ImmutableList
import com.example.newsaggregator.feature.newsfeed.domain.model.NewsItem
import com.example.newsaggregator.feature.newsfeed.presentation.component.NewsCardCell
import com.example.newsaggregator.feature.newsfeed.presentation.screen.feed.state.NewsFeedState
import com.example.newsaggregator.feature.newsfeed.presentation.viewmodel.NewsFeedViewModel
import androidx.compose.material3.MaterialTheme.typography as typography1
import androidx.compose.material3.Text as Text1

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
        onCategorySelect = { category -> viewModel.selectCategory(category) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NewsFeedContent(
    state: NewsFeedState,
    onRefresh: () -> Unit,
    onNewsClick: (String) -> Unit,
    onCategorySelect: (String) -> Unit = {},
) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text1("Новости") },
            actions = {
                IconButton(onClick = { /* Поиск */ }) {
                    Icon(Icons.Default.Search, "Поиск")
                }
            }
        )

        CategoriesRow(
            categories = state.categories,
            selectedCategory = state.selectedCategory,
            onCategorySelect = onCategorySelect
        )

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
private fun CategoriesRow(
    categories: ImmutableList<String>,
    selectedCategory: String,
    onCategorySelect: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(vertical = 8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(categories.size) { index ->
            val category = categories[index]
            CategoryChip(
                category = category,
                isSelected = category == selectedCategory,
                onClick = { onCategorySelect(category) }
            )
        }
    }
}

@Composable
private fun CategoryChip(
    category: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (isSelected) colorScheme.primary else colorScheme.surface
    val contentColor = if (isSelected) colorScheme.onPrimary else colorScheme.onSurface
    val borderColor = if (isSelected) Color.Transparent else colorScheme.outline

    Box(
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = MaterialTheme.shapes.medium
            )
            .background(backgroundColor)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text1(
            text = category.replaceFirstChar { it.titlecase() },
            color = contentColor,
            style = typography1.labelLarge
        )
    }
}

@Composable
private fun NewsList(
    news: ImmutableList<NewsItem>,
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
        Text1("Новости не найдены", style = typography1.bodyMedium)
    }
}

@Composable
private fun LoadingView() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.surface.copy(alpha = 0.9f)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(64.dp),
                color = colorScheme.primary,
                trackColor = colorScheme.surfaceVariant,
                strokeCap = StrokeCap.Round,
                strokeWidth = 6.dp
            )
            Text1(
                text = "Загрузка...",
                style = MaterialTheme.typography.bodyLarge,
                color = colorScheme.onSurface
            )
        }
    }
}

@Composable
private fun ErrorView(error: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text1(text = error, color = colorScheme.error)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Text1("Повторить")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NewsFeedScreenPreview() {
    MaterialTheme {
        NewsFeedContent(
            state = NewsFeedState(
                newsItems = ImmutableList(
                    listOf(
                        NewsItem(
                            id = "1",
                            title = "Пример новости 1",
                            description = "Краткое описание новости 1",
                            publishedAt = "2023-05-15",
                            url = "",
                            imageUrl = null,
                            source = "source 1",
                            content = "TODO()",
                            author = "author 1",
                            isFavorite = true,
                            category = null,
                        ),
                        NewsItem(
                            id = "2",
                            title = "Пример новости 2",
                            description = "Краткое описание новости 2",
                            publishedAt = "2023-05-16",
                            url = "",
                            imageUrl = null,
                            source = "source 2",
                            content = "TODO()",
                            author = "author 2",
                            isFavorite = false,
                            category = null,
                        )
                    )
                )
            ),
            onRefresh = {},
            onNewsClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LoadingPreview() {
    MaterialTheme {
        LoadingView()
    }
}

@Preview(showBackground = true)
@Composable
private fun ErrorPreview() {
    MaterialTheme {
        ErrorView(error = "Ошибка загрузки", onRetry = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun EmptyPreview() {
    MaterialTheme {
        EmptyStateView()
    }
}