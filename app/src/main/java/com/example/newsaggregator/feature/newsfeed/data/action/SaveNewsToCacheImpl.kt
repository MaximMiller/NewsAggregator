package com.example.newsaggregator.feature.newsfeed.data.action

import com.example.newsaggregator.feature.favorites.domain.action.IsFavoriteCheckAction
import com.example.newsaggregator.feature.newsfeed.data.local.dao.NewsDao
import com.example.newsaggregator.feature.newsfeed.data.mapper.NewsMapper
import com.example.newsaggregator.feature.newsfeed.domain.action.SaveCategoryNewsToCacheAction
import com.example.newsaggregator.feature.newsfeed.domain.action.SaveNewsToCacheAction
import com.example.newsaggregator.feature.newsfeed.domain.model.FeedType
import com.example.newsaggregator.feature.newsfeed.domain.model.NewsItem
import javax.inject.Inject

class SaveNewsToCacheImpl @Inject constructor(
    private val dao: NewsDao,
    private val mapper: NewsMapper,
    private val isFavoriteCheckAction: IsFavoriteCheckAction
) : SaveNewsToCacheAction, SaveCategoryNewsToCacheAction {
    override suspend operator fun invoke(items: List<NewsItem>, feedType: FeedType, page: Int) {
        val entitiesWithCorrectFavorites = items.map { newsItem ->
            val isFavorite = isFavoriteCheckAction(newsItem.url)
            newsItem.copy(isFavorite = isFavorite)
        }.map {
            mapper.domainToEntity(it, feedType, page)
        }
        dao.insertAll(entitiesWithCorrectFavorites)
    }

    override suspend operator fun invoke(
        items: List<NewsItem>,
        feedType: FeedType,
        category: String
    ) {
        val entitiesWithCorrectFavorites = items.map { newsItem ->
            val isFavorite = isFavoriteCheckAction(newsItem.url)
            newsItem.copy(isFavorite = isFavorite)
        }.map {
            mapper.domainToEntity(domain = it, feedType = feedType, category = category)
        }
        dao.insertAll(entitiesWithCorrectFavorites)
    }
}
