package com.example.newsaggregator.feature.favorites.domain.action

interface IsFavoriteCheckAction {
    suspend operator fun invoke(newsId: Long): Boolean
}