package com.example.newsaggregator.feature.favorites.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsaggregator.feature.favorites.data.local.entity.FavoriteEntity
import com.example.newsaggregator.feature.newsfeed.data.local.entity.NewsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(favorite: FavoriteEntity)

    @Query("""
        SELECT news.* 
        FROM news 
        INNER JOIN favorites ON news.url = favorites.news_url
        ORDER BY favorites.saved_at DESC
    """)
    fun getFavoritesWithNews(): Flow<List<NewsEntity>>

    @Query("DELETE FROM favorites WHERE news_url = :newsUrl")
    suspend fun removeFavorite(newsUrl: String)

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE news_url = :newsUrl LIMIT 1)")
    suspend fun isNewsFavorite(newsUrl: String): Boolean

}