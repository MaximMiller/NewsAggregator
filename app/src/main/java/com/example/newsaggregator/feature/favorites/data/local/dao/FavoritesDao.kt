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
        INNER JOIN favorites ON news.id = favorites.news_id
        ORDER BY favorites.saved_at DESC
    """)
    fun getFavoritesWithNews(): Flow<List<NewsEntity>>

    @Query("DELETE FROM favorites WHERE news_id= :newsId")
    suspend fun removeFavorite(newsId: Long)

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE news_id = :newsId LIMIT 1)")
    suspend fun isNewsFavorite(newsId: Long): Boolean

}