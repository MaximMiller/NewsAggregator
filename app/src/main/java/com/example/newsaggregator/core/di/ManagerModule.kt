package com.example.newsaggregator.core.di

import com.example.newsaggregator.core.FavoritesManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ManagerModule {

    @Provides
    @Singleton
    fun provideFavoritesManager(): FavoritesManager {
        return FavoritesManager()
    }

}