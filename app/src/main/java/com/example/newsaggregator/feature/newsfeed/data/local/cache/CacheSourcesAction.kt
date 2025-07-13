package com.example.newsaggregator.feature.newsfeed.data.local.cache

import com.example.newsaggregator.feature.newsfeed.data.local.dao.SourcesDao
import com.example.newsaggregator.feature.newsfeed.data.mapper.SourceMapper
import com.example.newsaggregator.feature.newsfeed.domain.model.SourceItem
import javax.inject.Inject

class CacheSourcesAction @Inject constructor(
    private val dao: SourcesDao,
    private val mapper: SourceMapper
) {
    suspend fun getByFilters(
        category: String?,
        language: String?,
        country: String?
    ): List<SourceItem> =
        dao.getByFilters(category, language, country).map(mapper::entityToDomain)

    suspend fun saveSources(sources: List<SourceItem>) {
        dao.upsertAll(sources.map(mapper::domainToEntity))
    }
}