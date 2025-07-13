package com.example.newsaggregator.feature.newsfeed.data.action

import com.example.newsaggregator.feature.newsfeed.data.mapper.SourceMapper
import com.example.newsaggregator.feature.newsfeed.data.remote.api.NewsApi
import com.example.newsaggregator.feature.newsfeed.domain.action.GetSourcesAction
import com.example.newsaggregator.feature.newsfeed.domain.model.SourceItem
import java.io.IOException
import javax.inject.Inject

internal class GetSourcesActionImpl @Inject constructor(
    private val api: NewsApi,
    private val mapper: SourceMapper
) : GetSourcesAction {
    override suspend fun invoke(
        category: String?,
        language: String?,
        country: String?
    ): List<SourceItem> {
        val response = api.getSources(category, language, country)
        if (!response.isSuccessful || response.body() == null) {
            throw IOException("Failed to fetch sources: ${response.code()}")
        }
        return response.body()!!.sources.map { sourceDto ->
            val entity = mapper.dtoToEntity(
                dto = sourceDto,
                category = category,
                language = language,
                country = country
            )
            mapper.entityToDomain(entity)
        }
    }
}