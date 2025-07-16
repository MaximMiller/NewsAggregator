package com.example.newsaggregator.feature.newsfeed.data.action

import com.example.newsaggregator.feature.newsfeed.data.local.dao.SourcesDao
import com.example.newsaggregator.feature.newsfeed.data.mapper.SourceMapper
import com.example.newsaggregator.feature.newsfeed.domain.action.SaveSourcesToCacheAction
import com.example.newsaggregator.feature.newsfeed.domain.model.SourceItem
import javax.inject.Inject

class SaveSourcesToCacheImpl @Inject constructor(
    private val dao: SourcesDao,
    private val mapper: SourceMapper
) : SaveSourcesToCacheAction {
    override suspend operator fun invoke(sources: List<SourceItem>) {
        dao.upsertAll(sources.map(mapper::domainToEntity))
    }
}