package com.example.newsaggregator.feature.newsfeed.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsaggregator.feature.newsfeed.data.local.entity.NewsEntity

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(news: List<NewsEntity>)

    @Query("SELECT * FROM news ORDER BY published_at DESC")
    suspend fun getAllNews(): List<NewsEntity>

    @Query("DELETE FROM news WHERE is_favorite = 0")
    suspend fun clearNonFavorites()

    @Query("SELECT * FROM news WHERE search_query = :query AND page = :page ORDER BY published_at DESC")
    suspend fun getBySearchQuery(query: String, page: Int): List<NewsEntity>

    @Query("SELECT * FROM news WHERE category = :category AND feed_type = 'CATEGORY'")
    suspend fun getNewsByCategory(category: String): List<NewsEntity>

    @Query("SELECT * FROM news WHERE url = :id")
    suspend fun getNewsById(id: Long): NewsEntity?
}