package com.example.newsaggregator.feature.newsfeed.domain.usecase

import android.util.Log
import com.example.newsaggregator.feature.newsfeed.data.local.cache.CacheNewsAction
import com.example.newsaggregator.feature.newsfeed.domain.action.SearchNewsAction
import com.example.newsaggregator.feature.newsfeed.domain.model.FeedType
import com.example.newsaggregator.feature.newsfeed.domain.model.NewsItem
import javax.inject.Inject

class SearchNewsUseCase @Inject constructor(
    private val searchAction: SearchNewsAction,
    private val cacheAction: CacheNewsAction,
) {
    suspend operator fun invoke(
        query: String,
        page: Int = 1
    ): Result<List<NewsItem>> {
        require(query.isNotBlank()) { "Query cannot be empty" }

        return runCatching {
            val results = searchAction(query, page)
            cacheAction.saveHeadlines(results, FeedType.SEARCH, page)
            results
        }.recover { error ->
            Log.e(
                "SearchNewsUseCase",
                "Search failed for query: '$query', page: $page",
                error
            )
            cacheAction.getBySearchQuery(query, page)
        }
    }
}