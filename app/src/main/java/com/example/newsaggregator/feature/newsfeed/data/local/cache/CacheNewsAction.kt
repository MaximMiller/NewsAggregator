package com.example.newsaggregator.feature.newsfeed.data.local.cache

import com.example.newsaggregator.feature.newsfeed.data.local.dao.NewsDao
import com.example.newsaggregator.feature.newsfeed.data.mapper.NewsMapper
import com.example.newsaggregator.feature.newsfeed.domain.model.FeedType
import com.example.newsaggregator.feature.newsfeed.domain.model.NewsItem

import javax.inject.Inject

class CacheNewsAction @Inject constructor(
    private val dao: NewsDao,
    private val mapper: NewsMapper
) {
    suspend fun getHeadlines(): List<NewsItem> =
        dao.getAllNews().map(mapper::entityToDomain)

    suspend fun getBySearchQuery(query: String, page: Int): List<NewsItem> =
        dao.getBySearchQuery(query, page).map(mapper::entityToDomain)

    suspend fun saveHeadlines(items: List<NewsItem>, feedType: FeedType, page: Int) {
        dao.insertAll(items.map {
            mapper.domainToEntity(it, feedType, page)
        })
    }
}