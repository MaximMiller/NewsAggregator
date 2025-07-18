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
        if (isFavoriteCheck(item.url)) {
            removeAction(item.url)
        } else {
            saveAction(item)
        }
    }
}