package com.example.newsaggregator.core

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

class FavoritesManager @Inject constructor() {
    private val _favoritesUpdates = MutableSharedFlow<Unit>()
    val favoritesUpdates: SharedFlow<Unit> = _favoritesUpdates.asSharedFlow()

    suspend fun notifyFavoritesChanged() {
        _favoritesUpdates.emit(Unit)
    }
}