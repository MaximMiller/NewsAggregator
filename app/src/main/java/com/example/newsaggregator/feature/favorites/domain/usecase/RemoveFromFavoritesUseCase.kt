package com.example.newsaggregator.feature.favorites.domain.usecase

import com.example.newsaggregator.feature.favorites.domain.action.RemoveFavoriteAction
import javax.inject.Inject

class RemoveFromFavoritesUseCase @Inject constructor(
    private val removeFavoriteAction: RemoveFavoriteAction
) {
    suspend operator fun invoke(newsUrl: String) {
        removeFavoriteAction(newsUrl)
    }
}