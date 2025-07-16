package com.example.newsaggregator.feature.newsfeed.data.action

import com.example.newsaggregator.feature.newsfeed.data.mapper.NewsMapper
import com.example.newsaggregator.feature.newsfeed.data.remote.api.NewsApi
import com.example.newsaggregator.feature.newsfeed.domain.action.SearchNewsAction
import com.example.newsaggregator.feature.newsfeed.domain.model.FeedType
import com.example.newsaggregator.feature.newsfeed.domain.model.NewsItem
import java.io.IOException
import javax.inject.Inject

internal class SearchNewsActionImpl @Inject constructor(
    private val api: NewsApi,
    private val mapper: NewsMapper
) : SearchNewsAction {
    override suspend operator fun invoke(
        query: String,
        page: Int
    ): List<NewsItem> {
        val response = api.searchNews(query = query, page = page)
        if (!response.isSuccessful || response.body() == null) {
            throw IOException("Failed to search news: ${response.code()}")
        }
        return response.body()!!.articles.map { articleDto ->
            val entity = mapper.dtoToEntity(
                dto = articleDto,
                feedType = FeedType.SEARCH,
                page = page,
                searchQuery = query
            )
            mapper.entityToDomain(entity)
        }
    }
}