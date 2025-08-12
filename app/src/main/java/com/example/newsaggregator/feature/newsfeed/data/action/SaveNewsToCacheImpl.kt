package com.example.newsaggregator.feature.newsfeed.data.action

import com.example.newsaggregator.feature.newsfeed.data.local.dao.NewsDao
import com.example.newsaggregator.feature.newsfeed.data.mapper.NewsMapper
import com.example.newsaggregator.feature.newsfeed.domain.action.SaveCategoryNewsToCacheAction
import com.example.newsaggregator.feature.newsfeed.domain.action.SaveNewsToCacheAction
import com.example.newsaggregator.feature.newsfeed.domain.model.FeedType
import com.example.newsaggregator.feature.newsfeed.domain.model.NewsItem
import javax.inject.Inject

class SaveNewsToCacheImpl @Inject constructor(
    private val dao: NewsDao,
    private val mapper: NewsMapper
) : SaveNewsToCacheAction, SaveCategoryNewsToCacheAction {
    override suspend operator fun invoke(items: List<NewsItem>, feedType: FeedType, page: Int) {
        dao.insertAll(items.map {
            mapper.domainToEntity(it, feedType, page)
        })
    }

    override suspend operator fun invoke(
        items: List<NewsItem>,
        feedType: FeedType,
        category: String
    ) { dao.insertAll(items.map {
            mapper.domainToEntity(domain = it, feedType = feedType, category = category)
        })
    }
}