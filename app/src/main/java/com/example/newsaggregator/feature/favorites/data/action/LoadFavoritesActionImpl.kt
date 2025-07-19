package com.example.newsaggregator.feature.favorites.data.action

import com.example.newsaggregator.feature.favorites.data.local.dao.FavoritesDao
import com.example.newsaggregator.feature.favorites.domain.action.LoadFavoritesAction
import com.example.newsaggregator.feature.newsfeed.data.mapper.NewsMapper
import com.example.newsaggregator.feature.newsfeed.domain.model.NewsItem
import kotlinx.coroutines.flow.first
import javax.inject.Inject

internal class LoadFavoritesActionImpl @Inject constructor(
    private val dao: FavoritesDao,
    private val newsMapper: NewsMapper
) : LoadFavoritesAction {
    override suspend fun invoke(): List<NewsItem> {
        return dao.getFavoritesWithNews()
            .first()
            .map { newsEntity ->
                newsMapper.entityToDomain(newsEntity)
            }
    }
}