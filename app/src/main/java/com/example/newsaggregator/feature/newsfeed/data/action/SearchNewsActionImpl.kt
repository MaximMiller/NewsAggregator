package com.example.newsaggregator.feature.newsfeed.data.action

import com.example.newsaggregator.feature.newsfeed.data.local.dao.NewsDao
import com.example.newsaggregator.feature.newsfeed.data.mapper.NewsMapper
import com.example.newsaggregator.feature.newsfeed.data.remote.api.NewsApi
import com.example.newsaggregator.feature.newsfeed.domain.action.SearchNewsAction
import com.example.newsaggregator.feature.newsfeed.domain.model.FeedType
import com.example.newsaggregator.feature.newsfeed.domain.model.NewsItem
import java.io.IOException
import javax.inject.Inject

 class SearchNewsActionImpl @Inject constructor(
    private val api: NewsApi,
    private val dao: NewsDao,
    private val mapper: NewsMapper
) : SearchNewsAction {

    override suspend operator fun invoke(
        query: String,
        page: Int
    ): Result<List<NewsItem>> = runCatching {
        val response = api.searchNews(
            query = query,
            page = page
        )

        if (!response.isSuccessful || response.body() == null) {
            throw IOException("Failed to search news: ${response.code()}")
        }

        val networkResult = response.body()!!

        val entities = networkResult.articles.map { article ->
            mapper.dtoToEntity(
                dto = article,
                feedType = FeedType.SEARCH,
                page = page,
                searchQuery = query
            )
        }
        dao.insertAll(entities)

        entities.map(mapper::entityToDomain)
    }.recoverCatching { error ->
        dao.getBySearchQuery(query, page)
            .takeIf { it.isNotEmpty() }
            ?.map(mapper::entityToDomain)
            ?: throw error
    }
}