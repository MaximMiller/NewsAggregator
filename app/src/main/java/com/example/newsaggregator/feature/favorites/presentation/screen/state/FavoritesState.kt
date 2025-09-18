package com.example.newsaggregator.feature.favorites.presentation.screen.state

import androidx.compose.runtime.Immutable
import com.example.newsaggregator.core.util.ImmutableList
import com.example.newsaggregator.feature.newsfeed.domain.model.NewsItem

@Immutable
data class FavoritesState(
    val favorites: ImmutableList<NewsItem> = ImmutableList(),
    val isLoading: Boolean = false,
    val error: String? = null
)