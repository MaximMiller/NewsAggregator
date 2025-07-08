package com.example.newsaggregator.feature.newsfeed.data.action

import com.example.newsaggregator.feature.newsfeed.data.local.dao.NewsDao
import com.example.newsaggregator.feature.newsfeed.data.mapper.NewsMapper
import com.example.newsaggregator.feature.newsfeed.data.remote.api.NewsApi
import com.example.newsaggregator.feature.newsfeed.domain.action.FetchHeadlinesAction
import com.example.newsaggregator.feature.newsfeed.domain.model.FeedType
import com.example.newsaggregator.feature.newsfeed.domain.model.NewsItem
import kotlinx.coroutines.flow.first
import java.io.IOException
import javax.inject.Inject

class FetchHeadlinesActionImpl @Inject constructor(
    private val api: NewsApi,
    private val dao: NewsDao,
    private val mapper: NewsMapper
) : FetchHeadlinesAction {

    override suspend fun invoke(
        country: String,
        page: Int
    ): Result<List<NewsItem>> = runCatching {
        val response = api.getTopHeadlines(country, page = page)

        if (!response.isSuccessful || response.body() == null) {
            throw IOException("Failed to fetch headlines: ${response.code()}")
        }

        val networkResult = response.body()!!

        val entities = networkResult.articles.map {
            mapper.dtoToEntity(it, FeedType.HEADLINES, page)
        }
        dao.insertAll(entities)

        entities.map(mapper::entityToDomain)
    }.recoverCatching { error ->
        dao.getAllNews().first()
            .takeIf { it.isNotEmpty() }
            ?.map(mapper::entityToDomain)
            ?: throw error
    }
}