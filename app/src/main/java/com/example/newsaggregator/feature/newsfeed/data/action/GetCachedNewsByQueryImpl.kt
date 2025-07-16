package com.example.newsaggregator.feature.newsfeed.data.action

import com.example.newsaggregator.feature.newsfeed.data.local.dao.NewsDao
import com.example.newsaggregator.feature.newsfeed.data.mapper.NewsMapper
import com.example.newsaggregator.feature.newsfeed.domain.action.GetCachedNewsByQueryAction
import com.example.newsaggregator.feature.newsfeed.domain.model.NewsItem
import javax.inject.Inject

class GetCachedNewsByQueryImpl @Inject constructor(
    private val dao: NewsDao,
    private val mapper: NewsMapper
) : GetCachedNewsByQueryAction {
    override suspend operator fun invoke(query: String, page: Int): List<NewsItem> =
        dao.getBySearchQuery(query, page).map(mapper::entityToDomain)
}