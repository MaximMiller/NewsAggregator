package com.example.newsaggregator.feature.newsfeed.domain.action

import com.example.newsaggregator.feature.newsfeed.domain.model.NewsItem

interface FetchHeadlinesAction {
    suspend operator fun invoke(
        country: String,
        page: Int
    ): List<NewsItem>
}