package com.example.newsaggregator.feature.newsfeed.domain.usecase

import com.example.newsaggregator.feature.newsfeed.domain.action.GetSourcesAction
import com.example.newsaggregator.feature.newsfeed.domain.model.SourceItem
import javax.inject.Inject

class GetFilteredSourcesUseCase @Inject constructor(
    private val getSources: GetSourcesAction
) {
    suspend operator fun invoke(
        category: String? = null,
        language: String? = "en",
        country: String? = null
    ): Result<List<SourceItem>> {
        return getSources(category, language, country)
    }
}