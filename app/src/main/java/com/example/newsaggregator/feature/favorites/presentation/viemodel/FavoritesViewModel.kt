package com.example.newsaggregator.feature.favorites.presentation.viemodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsaggregator.core.util.toImmutableList
import com.example.newsaggregator.feature.favorites.data.FavoritesManager
import com.example.newsaggregator.feature.favorites.domain.usecase.GetFavoritesUseCase
import com.example.newsaggregator.feature.favorites.presentation.screen.state.FavoritesState
import com.example.newsaggregator.feature.newsfeed.domain.model.NewsItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val favoritesManager: FavoritesManager
) : ViewModel() {
    private val _state = MutableStateFlow(FavoritesState())
    val state: StateFlow<FavoritesState> = _state.asStateFlow()
    init {
        loadFavorites()
        observeFavoritesUpdates()
    }
    private fun observeFavoritesUpdates() {
        viewModelScope.launch {
            favoritesManager.favoritesUpdates.collect {
                loadFavorites()
            }
        }
    }
    fun loadFavorites() {
        _state.value = _state.value.copy(isLoading = true)
        viewModelScope.launch {
            try {
                val favorites = getFavoritesUseCase()
                _state.value = _state.value.copy(
                    favorites = favorites.toImmutableList(),
                    isLoading = false,
                    error = null
                )
            } catch (error: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = error.message ?: "Ошибка загрузки избранного"
                )
            }
        }
    }

    fun removeFromFavorites(newsItem: NewsItem) {
        viewModelScope.launch {
            favoritesManager.setFavoriteState(newsItem, false)
        }
    }
}
