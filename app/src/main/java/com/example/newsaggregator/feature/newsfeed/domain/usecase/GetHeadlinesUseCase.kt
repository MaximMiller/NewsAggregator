package com.example.newsaggregator.feature.newsfeed.domain.usecase

import android.util.Log
import com.example.newsaggregator.feature.newsfeed.domain.action.FetchHeadlinesAction
import com.example.newsaggregator.feature.newsfeed.domain.action.GetCachedHeadlinesAction
import com.example.newsaggregator.feature.newsfeed.domain.action.SaveNewsToCacheAction
import com.example.newsaggregator.feature.newsfeed.domain.model.FeedType
import com.example.newsaggregator.feature.newsfeed.domain.model.NewsItem
import javax.inject.Inject

class GetHeadlinesUseCase @Inject constructor(
    private val fetchAction: FetchHeadlinesAction,
    private val getFromCache: GetCachedHeadlinesAction,
    private val saveToCache: SaveNewsToCacheAction
) {
    suspend operator fun invoke(
        country: String = "us",
        page: Int = 1,
        forceRefresh: Boolean = false
    ): Result<List<NewsItem>> = runCatching {
        if (forceRefresh) {
            fetchAndCache(country, page)
        } else {
            try {
                fetchAndCache(country, page)
            } catch (e: Exception) {
                Log.e(
                    "GetHeadlinesUseCase",
                    "Error: ${e.message}. Params: country=$country, page=$page",
                    e
                )
                getFromCache()
            }
        }
    }

    private suspend fun fetchAndCache(country: String, page: Int): List<NewsItem> {
        val news = fetchAction(country, page)
        saveToCache(news, FeedType.HEADLINES, page)
        return news
    }
}