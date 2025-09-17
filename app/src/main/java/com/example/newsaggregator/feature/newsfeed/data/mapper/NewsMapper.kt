package com.example.newsaggregator.feature.newsfeed.data.mapper

import com.example.newsaggregator.feature.favorites.domain.action.IsFavoriteCheckAction
import com.example.newsaggregator.feature.newsfeed.data.local.entity.NewsEntity
import com.example.newsaggregator.feature.newsfeed.data.remote.dto.ArticleDto
import com.example.newsaggregator.feature.newsfeed.domain.model.FeedType
import com.example.newsaggregator.feature.newsfeed.domain.model.NewsItem
import javax.inject.Inject

class NewsMapper @Inject constructor(
    private val isFavoriteCheckAction: IsFavoriteCheckAction
) {

    suspend fun dtoToEntity(
        dto: ArticleDto,
        feedType: FeedType,
        page: Int? = null,
        searchQuery: String? = null,
        category: String? = null,
    ): NewsEntity = NewsEntity(
        id = generateStableId(dto.url),
        url = dto.url,
        title = dto.title,
        description = dto.description ?: "",
        author = dto.author,
        sourceId = dto.source?.id,
        sourceName = dto.source?.name ?: "Unknown",
        urlToImage = dto.urlToImage,
        publishedAt = dto.publishedAt ?: "",
        content = dto.content,
        isFavorite = isFavoriteCheckAction(generateStableId(dto.url)),
        feedType = feedType,
        page = page ?: 1,
        searchQuery = searchQuery,
        category = category,
    )

    fun entityToDomain(entity: NewsEntity): NewsItem = NewsItem(
        id = entity.id,
        title = entity.title,
        description = entity.description.takeIf { it.isNotEmpty() },
        url = entity.url,
        imageUrl = entity.urlToImage,
        publishedAt = entity.publishedAt,
        source = entity.sourceName,
        content = entity.content,
        author = entity.author,
        isFavorite = entity.isFavorite,
        category = entity.category
    )

    fun domainToEntity(
        domain: NewsItem,
        feedType: FeedType,
        page: Int? = null,
        searchQuery: String? = null,
        category: String? = null,
    ): NewsEntity = NewsEntity(
        id = domain.id,
        url = domain.url,
        title = domain.title,
        description = domain.description ?: "",
        author = null,
        sourceId = null,
        sourceName = domain.source,
        urlToImage = domain.imageUrl,
        publishedAt = domain.publishedAt,
        content = null,
        isFavorite = domain.isFavorite,
        feedType = feedType,
        page = page ?: 1,
        searchQuery = searchQuery,
        category = category,
    )

    private fun generateStableId(url: String): Long {
        return url.hashCode().toLong()
    }
}