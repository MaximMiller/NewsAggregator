package com.example.newsaggregator.feature.newsfeed.presentation.screen.feed.state

import androidx.compose.runtime.Immutable
import com.example.newsaggregator.core.util.ImmutableList
import com.example.newsaggregator.feature.newsfeed.domain.model.NewsItem

@Immutable
data class NewsFeedState(
    val newsItems: ImmutableList<NewsItem> = ImmutableList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val selectedCountry: String = "us",
    val selectedCategory: String = "general",
    val categories: ImmutableList<String> = ImmutableList(
        listOf(
            "general",
            "business",
            "health",
            "technology",
            "sports",
            "entertainment",
            "science"
        )
    )
) {
    companion object {
        val Empty = NewsFeedState()
    }
}
