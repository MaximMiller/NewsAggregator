package com.example.newsaggregator.feature.newsfeed.domain.action

import com.example.newsaggregator.feature.newsfeed.domain.model.NewsItem

interface GetCachedHeadlinesAction {
    suspend operator fun invoke(): List<NewsItem>
}