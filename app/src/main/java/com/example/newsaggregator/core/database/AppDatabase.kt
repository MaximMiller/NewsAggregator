package com.example.newsaggregator.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newsaggregator.feature.favorites.data.local.dao.FavoritesDao
import com.example.newsaggregator.feature.favorites.data.local.entity.FavoriteEntity
import com.example.newsaggregator.feature.newsfeed.data.local.dao.NewsDao
import com.example.newsaggregator.feature.newsfeed.data.local.entity.NewsEntity

@Database(
    entities = [
        NewsEntity::class,
        FavoriteEntity::class
    ],
    version = 1,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
    abstract fun favoritesDao(): FavoritesDao

}