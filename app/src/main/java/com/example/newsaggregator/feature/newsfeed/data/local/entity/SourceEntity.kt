package com.example.newsaggregator.feature.newsfeed.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "sources",
    indices = [
        Index(value = ["last_fetch_time"], name = "idx_sources_last_fetch_time"),
        Index(value = ["category"], name = "idx_sources_category"),
        Index(value = ["language"], name = "idx_sources_language"),
        Index(value = ["country"], name = "idx_sources_country")
    ]
)
data class SourceEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "category")
    val category: String,

    @ColumnInfo(name = "language")
    val language: String,

    @ColumnInfo(name = "country")
    val country: String,

    @ColumnInfo(name = "last_fetch_time")
    val lastFetchTime: Long = System.currentTimeMillis()
)