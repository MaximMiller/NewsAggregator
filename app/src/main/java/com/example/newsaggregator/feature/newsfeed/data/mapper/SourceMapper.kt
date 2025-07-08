package com.example.newsaggregator.feature.newsfeed.data.mapper

import com.example.newsaggregator.feature.newsfeed.data.local.entity.SourceEntity
import com.example.newsaggregator.feature.newsfeed.data.remote.dto.NewsSourceDto
import com.example.newsaggregator.feature.newsfeed.domain.model.SourceItem
import javax.inject.Inject

class SourceMapper @Inject constructor() {
    fun dtoToEntity(
        dto: NewsSourceDto,
        category: String?,
        language: String?,
        country: String?
    ): SourceEntity = SourceEntity(
        id = dto.id ?: generateId(dto),
        name = dto.name ?: "",
        description = dto.description ?: "",
        category = category ?: dto.category ?: "general",
        language = language ?: dto.language ?: "en",
        country = country ?: dto.country ?: "",
        lastFetchTime = System.currentTimeMillis()
    )

    fun entityToDomain(entity: SourceEntity): SourceItem = SourceItem(
        id = entity.id,
        name = entity.name,
        description = entity.description,
        category = entity.category,
        language = entity.language,
        country = entity.country
    )

    private fun generateId(dto: NewsSourceDto): String {
        return "${dto.name?.hashCode() ?: 0}_${dto.url?.hashCode() ?: 0}"
    }
}