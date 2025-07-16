package com.example.newsaggregator.feature.newsfeed.domain.action

import com.example.newsaggregator.feature.newsfeed.domain.model.NewsItem

interface GetCachedNewsByQueryAction {
    suspend operator fun invoke(query: String, page: Int): List<NewsItem>
}