package com.example.newsaggregator.feature.favorites.data

import com.example.newsaggregator.feature.favorites.domain.action.IsFavoriteCheckAction
import com.example.newsaggregator.feature.favorites.domain.action.RemoveFavoriteAction
import com.example.newsaggregator.feature.favorites.domain.action.SaveFavoriteAction
import com.example.newsaggregator.feature.newsfeed.domain.model.NewsItem
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

class FavoritesManager @Inject constructor(
    private val saveAction: SaveFavoriteAction,
    private val removeAction: RemoveFavoriteAction,
    private val isFavoriteCheckAction: IsFavoriteCheckAction
) {
    private val _favoritesUpdates = MutableSharedFlow<Unit>()
    val favoritesUpdates: SharedFlow<Unit> = _favoritesUpdates.asSharedFlow()
    private val _favoriteStates = mutableMapOf<String, Boolean>()

    suspend fun getFavoriteState(newsUrl: String): Boolean {
        return _favoriteStates[newsUrl] ?: isFavoriteCheckAction(newsUrl).also {
            _favoriteStates[newsUrl] = it
        }
    }

    suspend fun toggleFavorite(item: NewsItem) {
        val newsUrl = item.url
        val currentState = getFavoriteState(newsUrl)
        val newFavoriteState = !currentState

        _favoriteStates[newsUrl] = newFavoriteState

        if (newFavoriteState) {
            saveAction(item)
        } else {
            removeAction(newsUrl)
        }
        notifyFavoritesChanged()
    }

    suspend fun setFavoriteState(item: NewsItem, isFavorite: Boolean) {
        val newsUrl = item.url
        _favoriteStates[newsUrl] = isFavorite

        if (isFavorite) {
            saveAction(item)
        } else {
            removeAction(newsUrl)
        }
        notifyFavoritesChanged()
    }

    private suspend fun notifyFavoritesChanged() {
        _favoritesUpdates.emit(Unit)
    }
}
