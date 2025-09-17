package com.example.newsaggregator.feature.newsfeed.presentation.screen.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.newsaggregator.core.util.ImmutableList
import com.example.newsaggregator.feature.newsfeed.domain.model.NewsItem
import com.example.newsaggregator.feature.newsfeed.presentation.component.EmptyStateView
import com.example.newsaggregator.feature.newsfeed.presentation.component.ErrorView
import com.example.newsaggregator.feature.newsfeed.presentation.component.LoadingView
import com.example.newsaggregator.feature.newsfeed.presentation.component.NewsCardCell
import com.example.newsaggregator.feature.newsfeed.presentation.screen.feed.state.NewsFeedState
import com.example.newsaggregator.feature.newsfeed.presentation.screen.feed.state.NewsFeedState.NewsCategory
import com.example.newsaggregator.feature.newsfeed.presentation.viewmodel.NewsFeedViewModel

@Composable
fun NewsFeedScreen(
    viewModel: NewsFeedViewModel = hiltViewModel(),
    onNewsClick: (Long) -> Unit = {},
    onFavoritesClick: () -> Unit = {},
) {
    val state by viewModel.state.collectAsState()

    NewsFeedContent(
        state = state,
        onRefresh = { viewModel.loadHeadlines() },
        onNewsClick = onNewsClick,
        onCategorySelect = { category -> viewModel.selectCategory(category) },
        onFavoriteClick = { item -> viewModel.toggleFavorite(item) },
        onFavoritesClick = onFavoritesClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NewsFeedContent(
    state: NewsFeedState,
    onRefresh: () -> Unit,
    onNewsClick: (Long) -> Unit,
    onFavoriteClick: (NewsItem) -> Unit,
    onCategorySelect: (String) -> Unit = {},
    onFavoritesClick: () -> Unit = {}
) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text("Новости") },
            actions = {
                IconButton(onClick = onFavoritesClick) {
                    Icon(Icons.Default.Favorite, "Избранное", tint =colorScheme.onSurface.copy(red = 0.6f))
                }
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
                onNewsClick = onNewsClick,
                onFavoriteClick = onFavoriteClick,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
private fun CategoriesRow(
    categories: ImmutableList<NewsCategory>,
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
                category = category.name,
                isSelected = category.name == selectedCategory,
                onClick = { onCategorySelect(category.name) }
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
        Text(
            text = category.replaceFirstChar { it.titlecase() },
            color = contentColor,
            style = typography.labelLarge
        )
    }
}

@Composable
private fun NewsList(
    news: ImmutableList<NewsItem>,
    onNewsClick: (Long) -> Unit,
    onFavoriteClick: (NewsItem) -> Unit,
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
            items = news,
            key = { it.id }
        ) { item ->
            NewsCardCell(
                newsItem = item,
                onClick = { onNewsClick(item.id) },
                onFavoriteClick = { onFavoriteClick(item) },
                isFavorite = item.isFavorite,
                modifier = Modifier.fillMaxWidth()
            )
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
                            id = 1,
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
                            id = 2,
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
            onNewsClick = {},
            onFavoriteClick = TODO(),
            onCategorySelect = TODO(),
            onFavoritesClick = TODO()
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