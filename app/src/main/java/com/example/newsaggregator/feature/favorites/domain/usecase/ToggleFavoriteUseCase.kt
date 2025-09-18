package com.example.newsaggregator.feature.favorites.domain.usecase

import com.example.newsaggregator.feature.favorites.domain.action.IsFavoriteCheckAction
import com.example.newsaggregator.feature.favorites.domain.action.RemoveFavoriteAction
import com.example.newsaggregator.feature.favorites.domain.action.SaveFavoriteAction
import com.example.newsaggregator.feature.newsfeed.domain.model.NewsItem
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val saveAction: SaveFavoriteAction,
    private val removeAction: RemoveFavoriteAction,
    private val isFavoriteCheck: IsFavoriteCheckAction
) {
    suspend operator fun invoke(item: NewsItem) {
        val newsUrl = item.url
        val isCurrentlyFavorite = isFavoriteCheck(newsUrl)

        if (isCurrentlyFavorite) {
            removeAction(newsUrl)
        } else {
            saveAction(item.copy(isFavorite = true))
        }
    }
}