package com.example.newsaggregator.feature.newsfeed.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import com.example.newsaggregator.feature.newsfeed.domain.model.FeedType

@Entity(
    tableName = "news",
    indices = [
        Index(value = ["published_at"], name = "idx_news_published_at"),
        Index(value = ["is_favorite"], name = "idx_news_is_favorite"),
        Index(value = ["category"], name = "idx_news_category"),
    ],
    primaryKeys = ["url"]
)
data class NewsEntity(
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "author") val author: String?,
    @ColumnInfo(name = "source_id") val sourceId: String?,
    @ColumnInfo(name = "source_name") val sourceName: String,
    @ColumnInfo(name = "url_to_image") val urlToImage: String?,
    @ColumnInfo(name = "published_at") val publishedAt: String,
    @ColumnInfo(name = "content") val content: String?,
    @ColumnInfo(name = "feed_type") val feedType: FeedType,
    @ColumnInfo(name = "page") val page: Int,
    @ColumnInfo(name = "search_query") val searchQuery: String?,
    @ColumnInfo(name = "category", index = true) val category: String?,
    @ColumnInfo(name = "is_favorite", defaultValue = "0")
    val isFavorite: Boolean = false
)