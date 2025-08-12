package com.example.newsaggregator.feature.newsfeed.domain.action

import com.example.newsaggregator.feature.newsfeed.domain.model.NewsItem

interface FetchHeadlinesByCategoryAction {
    suspend operator fun invoke(
        country: String,
        category: String
    ): List<NewsItem>
}