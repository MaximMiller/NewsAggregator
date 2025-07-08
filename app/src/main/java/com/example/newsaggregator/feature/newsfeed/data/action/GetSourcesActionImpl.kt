package com.example.newsaggregator.feature.newsfeed.data.action

import com.example.newsaggregator.feature.newsfeed.data.local.dao.SourcesDao
import com.example.newsaggregator.feature.newsfeed.data.mapper.SourceMapper
import com.example.newsaggregator.feature.newsfeed.data.remote.api.NewsApi
import com.example.newsaggregator.feature.newsfeed.domain.action.GetSourcesAction
import com.example.newsaggregator.feature.newsfeed.domain.model.SourceItem
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class GetSourcesActionImpl @Inject constructor(
    private val api: NewsApi,
    private val dao: SourcesDao,
    private val mapper: SourceMapper
) : GetSourcesAction {

    override suspend fun invoke(
        category: String?,
        language: String?,
        country: String?
    ): Result<List<SourceItem>> = runCatching {
        val response = api.getSources(category, language, country)

        if (!response.isSuccessful || response.body() == null) {
            throw IOException("Failed to fetch sources: ${response.code()}")
        }

        val networkResult = response.body()!!

        val currentTime = System.currentTimeMillis()
        val entities = networkResult.sources.map { dto ->
            mapper.dtoToEntity(dto, category, language, country)
                .copy(lastFetchTime = currentTime)
        }

        dao.upsertAll(entities)
        dao.clearOld(currentTime - TimeUnit.DAYS.toMillis(7))

        entities.map(mapper::entityToDomain)
    }.recoverCatching { error ->
        val cached = dao.getByFilters(category, language, country)
            .takeIf { it.isNotEmpty() }
            ?: throw error

        cached.map(mapper::entityToDomain)
    }
}