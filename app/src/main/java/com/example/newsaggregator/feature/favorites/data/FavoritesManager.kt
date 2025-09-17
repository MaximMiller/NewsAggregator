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
    private val _favoriteStates = mutableMapOf<Long, Boolean>()

    suspend fun getFavoriteState(newsId: Long): Boolean {
        return _favoriteStates[newsId] ?: isFavoriteCheckAction(newsId).also {
            _favoriteStates[newsId] = it
        }
    }

    suspend fun toggleFavorite(item: NewsItem) {
        val stableId = item.getStableId()
        val currentState = getFavoriteState(stableId)
        val newFavoriteState = !currentState

        _favoriteStates[stableId] = newFavoriteState

        if (newFavoriteState) {
            saveAction(item)
        } else {
            removeAction(stableId)
        }
        notifyFavoritesChanged()
    }

    suspend fun removeFromFavorites(newsId: Long) {
        _favoriteStates[newsId] = false
        removeAction(newsId)
        notifyFavoritesChanged()
    }

    suspend fun setFavoriteState(item: NewsItem, isFavorite: Boolean) {
        val stableId = item.getStableId()
        _favoriteStates[stableId] = isFavorite

        if (isFavorite) {
            saveAction(item)
        } else {
            removeAction(stableId)
        }
        notifyFavoritesChanged()
    }

    fun clearCache() {
        _favoriteStates.clear()
    }

    private suspend fun notifyFavoritesChanged() {
        _favoritesUpdates.emit(Unit)
    }
}
