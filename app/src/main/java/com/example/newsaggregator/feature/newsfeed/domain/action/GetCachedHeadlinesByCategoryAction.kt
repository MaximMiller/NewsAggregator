package com.example.newsaggregator.feature.newsfeed.domain.action

import com.example.newsaggregator.feature.newsfeed.domain.model.NewsItem

interface GetCachedHeadlinesByCategoryAction {
    suspend operator fun invoke(category: String): List<NewsItem>
}