package com.example.newsaggregator.feature.favorites.data.action

import com.example.newsaggregator.feature.favorites.data.local.dao.FavoritesDao
import com.example.newsaggregator.feature.favorites.domain.action.RemoveFavoriteAction
import javax.inject.Inject

internal class RemoveFavoriteActionImpl @Inject constructor(
    private val dao: FavoritesDao
) : RemoveFavoriteAction {
    override suspend fun invoke(newsId: Long) {
        dao.removeFavorite(newsId)
    }
}