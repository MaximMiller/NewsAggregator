package com.example.newsaggregator.feature.favorites.data.action

import com.example.newsaggregator.feature.favorites.data.local.dao.FavoritesDao
import com.example.newsaggregator.feature.favorites.data.mapper.FavoriteMapper
import com.example.newsaggregator.feature.favorites.domain.action.SaveFavoriteAction
import com.example.newsaggregator.feature.newsfeed.domain.model.NewsItem
import javax.inject.Inject

internal class SaveFavoriteActionImpl @Inject constructor(
    private val favoritesDao: FavoritesDao,
    private val mapper: FavoriteMapper,
) : SaveFavoriteAction {
    override suspend fun invoke(item: NewsItem) {
        favoritesDao.addFavorite(
            mapper.domainToEntity(
                item = item,
                savedAt = System.currentTimeMillis()
            )
        )
    }
}