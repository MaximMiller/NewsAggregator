package com.example.newsaggregator.feature.newsfeed.domain.action

import com.example.newsaggregator.feature.newsfeed.domain.model.FeedType
import com.example.newsaggregator.feature.newsfeed.domain.model.NewsItem

interface SaveCategoryNewsToCacheAction {
    suspend operator fun invoke(
        items: List<NewsItem>,
        feedType: FeedType,
        category: String,
    )
}