package com.example.newsaggregator.feature.newsfeed.domain.usecase

import android.util.Log
import com.example.newsaggregator.feature.newsfeed.domain.action.GetCachedSourcesByFiltersAction
import com.example.newsaggregator.feature.newsfeed.domain.action.GetSourcesAction
import com.example.newsaggregator.feature.newsfeed.domain.action.SaveSourcesToCacheAction
import com.example.newsaggregator.feature.newsfeed.domain.model.SourceItem
import javax.inject.Inject

class GetSourcesUseCase @Inject constructor(
    private val getSourcesAction: GetSourcesAction,
    private val getFromCache: GetCachedSourcesByFiltersAction,
    private val saveToCache: SaveSourcesToCacheAction
) {
    suspend operator fun invoke(
        category: String? = null,
        language: String? = "en",
        country: String? = null
    ): Result<List<SourceItem>> = runCatching {
        try {
            val sources = getSourcesAction(category, language, country)
            saveToCache(sources)
            sources
        } catch (e: Exception) {
            Log.e(
                "GetSourcesUseCase",
                "Error: ${e.message}. Params: category=$category, lang=$language",
                e
            )
            val cachedSources = getFromCache(category, language, country)
            if (cachedSources.isNotEmpty()) {
                cachedSources
            } else {
                throw e
            }
        }
    }
}