package com.example.newsaggregator.feature.newsfeed.presentation.screen.detail.state

import androidx.compose.runtime.Immutable
import com.example.newsaggregator.feature.newsfeed.domain.model.NewsItem

@Immutable
data class NewsDetailState(
    val isLoading: Boolean = false,
    val newsItem: NewsItem? = null,
    val error: String? = null
)
