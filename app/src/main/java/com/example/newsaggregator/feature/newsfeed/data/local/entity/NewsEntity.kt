package com.example.newsaggregator.feature.newsfeed.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "news",
    indices = [
        Index(value = ["published_at"], name = "idx_news_published_at"),
        Index(value = ["is_favorite"], name = "idx_news_is_favorite")
    ]
)
data class NewsEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "author") val author: String?,
    @ColumnInfo(name = "source_id") val sourceId: String?,
    @ColumnInfo(name = "source_name") val sourceName: String,
    @ColumnInfo(name = "url_to_image") val urlToImage: String?,
    @ColumnInfo(name = "published_at") val publishedAt: String,
    @ColumnInfo(name = "content") val content: String?,
    @ColumnInfo(name = "feed_type") val feedType: String,
    @ColumnInfo(name = "page") val page: Int,
    @ColumnInfo(name = "search_query") val searchQuery: String?,

    @ColumnInfo(name = "is_favorite", defaultValue = "0")
    val isFavorite: Boolean = false
)