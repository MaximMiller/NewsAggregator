package com.example.newsaggregator.core.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
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

    companion object {
        private const val DATABASE_NAME = "news_aggregator.db"

        fun create(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                DATABASE_NAME
            )
                .addCallback(object : Callback() {
                    override fun onDestructiveMigration(db: SupportSQLiteDatabase) {
                        Log.w("AppDatabase", "Данные удалены из-за изменения схемы БД")
                    }
                })
                .fallbackToDestructiveMigrationOnDowngrade()
                .build()
        }
    }
}