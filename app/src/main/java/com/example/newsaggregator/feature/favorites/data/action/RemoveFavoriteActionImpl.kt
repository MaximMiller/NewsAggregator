package com.example.newsaggregator.feature.favorites.data.action

import com.example.newsaggregator.feature.favorites.data.local.dao.FavoritesDao
import com.example.newsaggregator.feature.favorites.domain.action.RemoveFavoriteAction
import com.example.newsaggregator.feature.newsfeed.data.local.dao.NewsDao
import javax.inject.Inject

internal class RemoveFavoriteActionImpl @Inject constructor(
    private val favoritesDao: FavoritesDao,
    private val newsDao: NewsDao,
) : RemoveFavoriteAction {
    override suspend fun invoke(newsId: Long) {
        favoritesDao.removeFavorite(newsId)
        newsDao.updateFavoriteStatus(newsId, false)
    }
}