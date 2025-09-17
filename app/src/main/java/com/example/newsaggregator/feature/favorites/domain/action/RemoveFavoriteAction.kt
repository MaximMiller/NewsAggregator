package com.example.newsaggregator.feature.favorites.domain.action

interface RemoveFavoriteAction {
    suspend operator fun invoke(newsId: Long)
}