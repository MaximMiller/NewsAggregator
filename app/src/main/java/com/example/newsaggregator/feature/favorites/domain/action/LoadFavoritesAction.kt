package com.example.newsaggregator.feature.favorites.domain.action

import com.example.newsaggregator.feature.newsfeed.domain.model.NewsItem

interface LoadFavoritesAction {
    suspend operator fun invoke(): List<NewsItem>

}