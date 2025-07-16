package com.example.newsaggregator.feature.newsfeed.data.action

import com.example.newsaggregator.feature.newsfeed.data.local.dao.SourcesDao
import com.example.newsaggregator.feature.newsfeed.data.mapper.SourceMapper
import com.example.newsaggregator.feature.newsfeed.domain.action.GetCachedSourcesByFiltersAction
import com.example.newsaggregator.feature.newsfeed.domain.model.SourceItem
import javax.inject.Inject

class GetCachedSourcesByFiltersImpl @Inject constructor(
    private val dao: SourcesDao,
    private val mapper: SourceMapper
) : GetCachedSourcesByFiltersAction {
    override suspend operator fun invoke(
        category: String?,
        language: String?,
        country: String?
    ): List<SourceItem> =
        dao.getByFilters(category, language, country).map(mapper::entityToDomain)
}