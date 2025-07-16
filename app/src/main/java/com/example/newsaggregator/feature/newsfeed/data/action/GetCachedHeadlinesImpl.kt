package com.example.newsaggregator.feature.newsfeed.data.action

import com.example.newsaggregator.feature.newsfeed.data.local.dao.NewsDao
import com.example.newsaggregator.feature.newsfeed.data.mapper.NewsMapper
import com.example.newsaggregator.feature.newsfeed.domain.action.GetCachedHeadlinesAction
import com.example.newsaggregator.feature.newsfeed.domain.model.NewsItem
import javax.inject.Inject

class GetCachedHeadlinesImpl @Inject constructor(
    private val dao: NewsDao,
    private val mapper: NewsMapper
) : GetCachedHeadlinesAction {
    override suspend operator fun invoke(): List<NewsItem> =
        dao.getAllNews().map(mapper::entityToDomain)
}