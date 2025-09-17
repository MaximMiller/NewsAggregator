package com.example.newsaggregator.core.di

import com.example.newsaggregator.feature.favorites.data.FavoritesManager
import com.example.newsaggregator.feature.favorites.domain.action.IsFavoriteCheckAction
import com.example.newsaggregator.feature.favorites.domain.action.RemoveFavoriteAction
import com.example.newsaggregator.feature.favorites.domain.action.SaveFavoriteAction
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
    fun provideFavoritesManager(
        saveAction: SaveFavoriteAction,
        removeAction: RemoveFavoriteAction,
        isFavoriteCheckAction: IsFavoriteCheckAction
    ): FavoritesManager = FavoritesManager(saveAction, removeAction, isFavoriteCheckAction)

}