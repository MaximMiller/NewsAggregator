package com.example.newsaggregator.feature.newsfeed.presentation.screen.feed.state

import androidx.compose.runtime.Immutable
import com.example.newsaggregator.core.util.ImmutableList
import com.example.newsaggregator.core.util.toImmutableList
import com.example.newsaggregator.feature.newsfeed.domain.model.NewsItem

@Immutable
data class NewsFeedState(
    val newsItems: ImmutableList<NewsItem> = ImmutableList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val selectedCountry: String = "us",
    val selectedCategory: String = "ALL",
    val categories: ImmutableList<NewsCategory> = NewsCategory.entries.toImmutableList()
) {
    companion object {
        val Empty = NewsFeedState()
    }

    enum class NewsCategory {
        ALL,
        GENERAL,
        BUSINESS,
        HEALTH,
        TECHNOLOGY,
        SPORTS,
        ENTERTAINMENT,
        SCIENCE;
    }
}
