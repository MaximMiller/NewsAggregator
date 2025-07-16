package com.example.newsaggregator.feature.newsfeed.domain.action

import com.example.newsaggregator.feature.newsfeed.domain.model.FeedType
import com.example.newsaggregator.feature.newsfeed.domain.model.NewsItem

interface SaveNewsToCacheAction {
    suspend operator fun invoke(items: List<NewsItem>, feedType: FeedType, page: Int)
}