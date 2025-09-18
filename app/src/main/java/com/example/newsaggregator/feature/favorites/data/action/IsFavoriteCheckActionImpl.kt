package com.example.newsaggregator.feature.favorites.data.action

import com.example.newsaggregator.feature.favorites.data.local.dao.FavoritesDao
import com.example.newsaggregator.feature.favorites.domain.action.IsFavoriteCheckAction
import javax.inject.Inject

internal class IsFavoriteCheckActionImpl @Inject constructor(
    private val dao: FavoritesDao
) : IsFavoriteCheckAction {
    override suspend fun invoke(newsUrl: String): Boolean {
        return dao.isNewsFavorite(newsUrl)
    }
}