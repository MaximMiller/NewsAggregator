package com.example.newsaggregator.feature.newsfeed.domain.usecase

import com.example.newsaggregator.feature.newsfeed.domain.action.GetCachedHeadlinesAction
import com.example.newsaggregator.feature.newsfeed.domain.model.NewsItem
import javax.inject.Inject

class GetNewsDetailUseCase @Inject constructor(
    private val getCachedHeadlinesAction: GetCachedHeadlinesAction
) {
    suspend operator fun invoke(newsUrl: String): Result<NewsItem> = runCatching {
        val allNews = getCachedHeadlinesAction()
        allNews.firstOrNull { it.url == newsUrl }
            ?: throw NoSuchElementException("Новость не найдена в кеше")
    }
}