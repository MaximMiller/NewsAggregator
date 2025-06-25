package com.example.newsaggregator.presentation.viewmodel



import androidx.compose.runtime.Recomposer
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsaggregator.domain.usecase.GetTopNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getTopNews: GetTopNewsUseCase
) : ViewModel() {
    private val _state = mutableStateOf(NewsState())
    val state: Recomposer.State<NewsState> = _state

    init {
        loadNews()
    }

    private fun loadNews() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                val news = getTopNews()
                _state.value = _state.value.copy(
                    articles = news,
                    isLoading = false
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    error = e.message,
                    isLoading = false
                )
            }
        }
    }
}