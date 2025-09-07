package com.example.newsaggregator.feature.newsfeed.domain.usecase

import android.util.Log
import com.example.newsaggregator.feature.newsfeed.domain.action.FetchHeadlinesByCategoryAction
import com.example.newsaggregator.feature.newsfeed.domain.action.GetCachedHeadlinesByCategoryAction
import com.example.newsaggregator.feature.newsfeed.domain.action.SaveCategoryNewsToCacheAction
import com.example.newsaggregator.feature.newsfeed.domain.model.FeedType
import com.example.newsaggregator.feature.newsfeed.domain.model.NewsItem
import javax.inject.Inject

class GetHeadlinesByCategoryUseCase @Inject constructor(
    private val fetchAction: FetchHeadlinesByCategoryAction,
    private val getFromCache: GetCachedHeadlinesByCategoryAction,
    private val saveToCache: SaveCategoryNewsToCacheAction,
) {
    suspend operator fun invoke(
        country: String = "us",
        category: String,
        forceRefresh: Boolean = false
    ): Result<List<NewsItem>> = runCatching {
        if (forceRefresh) {
            fetchAndCache(country, category)
        } else {
            try {
                fetchAndCache(country, category)
            } catch (e: Exception) {
                Log.e(
                    "GetHeadlinesByCategoryUseCase",
                    "Fetch failed. Fallback to cache. Params: country=$country, category=$category",
                    e
                )
                getFromCache(category)
            }
        }
    }

    private suspend fun fetchAndCache(country: String, category: String): List<NewsItem> {
        val news = fetchAction(country, category)
        saveToCache(news, FeedType.CATEGORY, category)
        return news
    }
}