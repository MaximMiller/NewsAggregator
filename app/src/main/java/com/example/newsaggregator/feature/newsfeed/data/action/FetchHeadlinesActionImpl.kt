package com.example.newsaggregator.feature.newsfeed.data.action

import com.example.newsaggregator.feature.newsfeed.data.mapper.NewsMapper
import com.example.newsaggregator.feature.newsfeed.data.remote.api.NewsApi
import com.example.newsaggregator.feature.newsfeed.domain.action.FetchHeadlinesAction
import com.example.newsaggregator.feature.newsfeed.domain.action.FetchHeadlinesByCategoryAction
import com.example.newsaggregator.feature.newsfeed.domain.model.FeedType
import com.example.newsaggregator.feature.newsfeed.domain.model.NewsItem
import java.io.IOException
import javax.inject.Inject

internal class FetchHeadlinesActionImpl @Inject constructor(
    private val api: NewsApi,
    private val mapper: NewsMapper
) : FetchHeadlinesAction, FetchHeadlinesByCategoryAction {
    override suspend fun invoke(
        country: String,
        page: Int
    ): List<NewsItem> {
        val response = api.getTopHeadlines(country, page = page)
        if (!response.isSuccessful || response.body() == null) {
            throw IOException("Failed to fetch headlines: ${response.code()}")
        }
        return response.body()!!.articles.map { articleDto ->
            val entity = mapper.dtoToEntity(
                dto = articleDto,
                feedType = FeedType.HEADLINES,
                page = page
            )
            mapper.entityToDomain(entity)
        }
    }

    override suspend fun invoke(country: String, category: String): List<NewsItem> {
        val response = api.getTopHeadlines(country, category)

        if (!response.isSuccessful || response.body() == null) {
            throw IOException("Failed to fetch headlines By Category: ${response.code()}")
        }
        return response.body()!!.articles.map { articleDto ->
            mapper.entityToDomain(
                mapper.dtoToEntity(
                    dto = articleDto,
                    feedType = FeedType.CATEGORY,
                )
            )
        }
    }
}