package com.example.newsaggregator.feature.favorites.presentation.screen.state

import com.example.newsaggregator.feature.newsfeed.domain.model.NewsItem

data class FavoritesState(
    val favorites: List<NewsItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)