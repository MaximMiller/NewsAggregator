package com.example.newsaggregator.feature.favorites.domain.action

import com.example.newsaggregator.feature.newsfeed.domain.model.NewsItem

interface SaveFavoriteAction {
    suspend operator fun invoke(item: NewsItem)
}