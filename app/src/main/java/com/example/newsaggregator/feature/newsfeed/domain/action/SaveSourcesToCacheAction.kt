package com.example.newsaggregator.feature.newsfeed.domain.action

import com.example.newsaggregator.feature.newsfeed.domain.model.SourceItem

interface SaveSourcesToCacheAction {
    suspend operator fun invoke(sources: List<SourceItem>)
}