package com.example.newsaggregator.feature.newsfeed.data.mapper

import com.example.newsaggregator.feature.newsfeed.data.local.entity.NewsEntity
import com.example.newsaggregator.feature.newsfeed.data.remote.dto.ArticleDto
import com.example.newsaggregator.feature.newsfeed.domain.model.FeedType
import com.example.newsaggregator.feature.newsfeed.domain.model.NewsItem
import javax.inject.Inject

class NewsMapper @Inject constructor() {
    fun dtoToEntity(
        dto: ArticleDto,
        feedType: FeedType,
        page: Int,
        searchQuery: String? = null
    ): NewsEntity = NewsEntity(
        url = dto.url,
        title = dto.title,
        description = dto.description ?: "",
        author = dto.author,
        sourceId = dto.source.id,
        sourceName = dto.source.name ?: "Unknown",
        urlToImage = dto.urlToImage,
        publishedAt = dto.publishedAt,
        content = dto.content,
        isFavorite = false,
        feedType = feedType.name,
        page = page,
        searchQuery = searchQuery
    )

    fun entityToDomain(entity: NewsEntity): NewsItem = NewsItem(
        id = entity.url,
        title = entity.title,
        description = entity.description.takeIf { it.isNotEmpty() },
        url = entity.url,
        imageUrl = entity.urlToImage,
        publishedAt = entity.publishedAt,
        source = entity.sourceName
    )
}