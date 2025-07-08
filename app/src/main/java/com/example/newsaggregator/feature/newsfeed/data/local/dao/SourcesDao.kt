package com.example.newsaggregator.feature.newsfeed.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsaggregator.feature.newsfeed.data.local.entity.SourceEntity

@Dao
interface SourcesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(sources: List<SourceEntity>)

    @Query("SELECT * FROM sources WHERE (:category IS NULL OR category = :category) AND (:language IS NULL OR language = :language) AND (:country IS NULL OR country = :country)")
    suspend fun getByFilters(
        category: String?,
        language: String?,
        country: String?
    ): List<SourceEntity>

    @Query("DELETE FROM sources WHERE last_fetch_time < :timestamp")
    suspend fun clearOld(timestamp: Long)
}