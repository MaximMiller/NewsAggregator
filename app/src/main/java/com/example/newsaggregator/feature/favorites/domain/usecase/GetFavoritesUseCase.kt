package com.example.newsaggregator.feature.favorites.domain.usecase

import com.example.newsaggregator.feature.favorites.domain.action.LoadFavoritesAction
import com.example.newsaggregator.feature.newsfeed.domain.model.NewsItem
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(
    private val loadAction: LoadFavoritesAction
) {
    suspend operator fun invoke(): List<NewsItem> = loadAction()
}