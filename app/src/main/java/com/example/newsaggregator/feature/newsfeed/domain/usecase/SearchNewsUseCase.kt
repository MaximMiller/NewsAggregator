package com.example.newsaggregator.feature.newsfeed.domain.usecase

import com.example.newsaggregator.feature.newsfeed.domain.action.SearchNewsAction
import com.example.newsaggregator.feature.newsfeed.domain.model.NewsItem
import javax.inject.Inject

class SearchNewsUseCase @Inject constructor(
    private val searchNews: SearchNewsAction
) {
    suspend operator fun invoke(
        query: String,
        page: Int = 1
    ): Result<List<NewsItem>> {
        require(query.isNotBlank()) { "Query cannot be empty" }
        return searchNews(query, page)
    }
}