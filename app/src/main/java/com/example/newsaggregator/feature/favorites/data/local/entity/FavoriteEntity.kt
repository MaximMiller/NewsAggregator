package com.example.newsaggregator.feature.favorites.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.newsaggregator.feature.newsfeed.data.local.entity.NewsEntity

@Entity(
    tableName = "favorites",
    foreignKeys = [ForeignKey(
        entity = NewsEntity::class,
        parentColumns = ["url"],
        childColumns = ["news_url"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [
        Index("news_url", unique = true),
        Index("saved_at")

    ]
)
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,

    @ColumnInfo(name = "news_url") val newsUrl: String,
    @ColumnInfo(name = "saved_at") val savedAt: Long = System.currentTimeMillis()
)