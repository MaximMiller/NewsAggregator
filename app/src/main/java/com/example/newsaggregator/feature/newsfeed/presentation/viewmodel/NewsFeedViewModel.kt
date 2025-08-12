package com.example.newsaggregator.feature.newsfeed.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsaggregator.core.util.toImmutableList
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
) : ViewModel() {
    private val _state = MutableStateFlow(NewsFeedState.Empty)
    val state: StateFlow<NewsFeedState> = _state.asStateFlow()

    init {
        loadHeadlines()
    }

    fun loadHeadlines() {
        _state.value = _state.value.copy(isLoading = true)
        viewModelScope.launch {
            getHeadlinesUseCase()
                .onSuccess { news ->
                    _state.update {
                        it.copy(
                            newsItems = news.toImmutableList(),
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

    fun selectCategory(category: String) {
        _state.update { it.copy(selectedCategory = category) }
        loadHeadlinesByCategory(category)
    }

    private fun loadHeadlinesByCategory(category: String) {
        _state.value = _state.value.copy(isLoading = true)
        viewModelScope.launch {
            getHeadlinesByCategoryUseCase(category = category)
                .onSuccess { news ->
                    _state.update {
                        it.copy(
                            newsItems = news.toImmutableList(),
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
}