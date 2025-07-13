package com.example.newsaggregator.feature.newsfeed.domain.action

import com.example.newsaggregator.feature.newsfeed.domain.model.SourceItem

interface GetSourcesAction {
    suspend operator fun invoke(
        category: String?,
        language: String?,
        country: String?
    ): List<SourceItem>
}