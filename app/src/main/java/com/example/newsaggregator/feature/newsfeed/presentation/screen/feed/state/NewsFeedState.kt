package com.example.newsaggregator.feature.newsfeed.presentation.screen.feed.state

import com.example.newsaggregator.feature.newsfeed.domain.model.NewsItem

data class NewsFeedState(
    val newsItems: List<NewsItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val selectedCountry: String = "us",
    val selectedCategory: String = "general",
    val categories: List<String> = listOf(
        "general",
        "business",
        "health",
        "technology",
        "sports",
        "entertainment",
        "science"
    )
) {
    companion object {
        val Empty = NewsFeedState()
    }
}