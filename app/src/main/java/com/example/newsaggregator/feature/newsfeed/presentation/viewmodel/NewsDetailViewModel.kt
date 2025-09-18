package com.example.newsaggregator.feature.newsfeed.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsaggregator.feature.newsfeed.domain.usecase.GetNewsDetailUseCase
import com.example.newsaggregator.feature.newsfeed.presentation.screen.detail.state.NewsDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsDetailViewModel @Inject constructor(
    private val getNewsDetailUseCase: GetNewsDetailUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(NewsDetailState())
    val state: StateFlow<NewsDetailState> = _state.asStateFlow()

    fun loadNewsDetail(newsUrl: String) {
        _state.value = _state.value.copy(isLoading = true)
        viewModelScope.launch {
            getNewsDetailUseCase(newsUrl)
                .onSuccess { newsItem ->
                    _state.value = _state.value.copy(
                        newsItem = newsItem,
                        isLoading = false,
                        error = null
                    )
                }
                .onFailure { error ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = error.message
                    )
                }
        }
    }
}
