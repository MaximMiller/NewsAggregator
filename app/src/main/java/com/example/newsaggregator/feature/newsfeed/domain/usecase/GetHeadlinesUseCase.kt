package com.example.newsaggregator.feature.newsfeed.domain.usecase

import com.example.newsaggregator.feature.newsfeed.domain.action.FetchHeadlinesAction
import com.example.newsaggregator.feature.newsfeed.domain.action.GetCachedHeadlinesAction
import com.example.newsaggregator.feature.newsfeed.domain.action.SaveNewsToCacheAction
import com.example.newsaggregator.feature.newsfeed.domain.model.FeedType
import com.example.newsaggregator.feature.newsfeed.domain.model.NewsItem
import javax.inject.Inject

class GetHeadlinesUseCase @Inject constructor(
    private val fetchAction: FetchHeadlinesAction,
    private val getFromCache: GetCachedHeadlinesAction,
    private val saveToCache: SaveNewsToCacheAction,
) {
    suspend operator fun invoke(
        country: String = "us",
        page: Int = 1,
        forceRefresh: Boolean = false
    ): Result<List<NewsItem>> = runCatching {
        val news =   if (forceRefresh) {
            fetchAndCache(country, page)
        } else {
            try {
                fetchAndCache(country, page)
            } catch (e: Exception) {
                getFromCache()
            }
        }
        return@runCatching news
    }

    private suspend fun fetchAndCache(country: String, page: Int): List<NewsItem> {
        val news = fetchAction(country, page)
        saveToCache(news, FeedType.HEADLINES, page)
        return news
    }
}