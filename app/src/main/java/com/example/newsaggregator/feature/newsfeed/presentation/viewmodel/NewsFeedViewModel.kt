package com.example.newsaggregator.feature.newsfeed.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsaggregator.feature.favorites.data.FavoritesManager
import com.example.newsaggregator.core.util.toImmutableList
import com.example.newsaggregator.feature.newsfeed.domain.model.NewsItem
import com.example.newsaggregator.feature.newsfeed.domain.usecase.GetHeadlinesByCategoryUseCase
import com.example.newsaggregator.feature.newsfeed.domain.usecase.GetHeadlinesUseCase
import com.example.newsaggregator.feature.newsfeed.presentation.screen.feed.state.NewsFeedState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsFeedViewModel @Inject constructor(
    private val getHeadlinesUseCase: GetHeadlinesUseCase,
    private val getHeadlinesByCategoryUseCase: GetHeadlinesByCategoryUseCase,
    private val favoritesManager: FavoritesManager
) : ViewModel() {
    private val _state = MutableStateFlow(NewsFeedState.Empty)
    val state: StateFlow<NewsFeedState> = _state.asStateFlow()
    init {
        loadHeadlines()
    }
        fun selectCategory(category: String) {
        _state.update { it.copy(selectedCategory = category) }
        if (category == "ALL") {
            loadHeadlines()
        } else {
            loadHeadlinesByCategory(category)
        }
    }

        private fun loadHeadlinesByCategory(category: String) {
        _state.value = _state.value.copy(isLoading = true)
        viewModelScope.launch {
            getHeadlinesByCategoryUseCase(category = category)
                .onSuccess { news ->
                    val syncedNews = syncNewsWithFavorites(news)
                    _state.update {
                        it.copy(
                            newsItems = syncedNews.toImmutableList(),
                            isLoading = false,
                            error = null
                        )
                    }
                }
                .onFailure { error ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = error.message
                        )
                    }
                }
        }
    }

    fun loadHeadlines() {
        _state.value = _state.value.copy(isLoading = true)
        viewModelScope.launch {
            getHeadlinesUseCase()
                .onSuccess { news ->
                    val syncedNews = syncNewsWithFavorites(news)
                    _state.update {
                        it.copy(
                            newsItems = syncedNews.toImmutableList(),
                            isLoading = false,
                            error = null
                        )
                    }
                }
                .onFailure { error ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = error.message
                        )
                    }
                }
        }
    }

    fun toggleFavorite(item: NewsItem) {
        viewModelScope.launch {
            val newFavoriteState = !item.isFavorite
            _state.update { currentState ->
                val updatedItems = currentState.newsItems.map { news ->
                    if (news.url == item.url) {
                        news.copy(isFavorite = newFavoriteState)
                    } else {
                        news
                    }
                }.toImmutableList()
                currentState.copy(newsItems = updatedItems)
            }

            favoritesManager.toggleFavorite(item)
        }
    }

    private suspend fun syncNewsWithFavorites(news: List<NewsItem>): List<NewsItem> {
        return news.map { item ->
            val isFavorite = favoritesManager.getFavoriteState(item.url)
            item.copy(isFavorite = isFavorite)
        }
    }
}