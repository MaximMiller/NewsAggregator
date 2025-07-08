package com.example.newsaggregator.feature.newsfeed.domain.action

import com.example.newsaggregator.feature.newsfeed.domain.model.NewsItem

interface SearchNewsAction {
    suspend operator fun invoke(
        query: String,
        page: Int
    ): Result<List<NewsItem>>
}