package com.example.newsaggregator.presentation.screens.news

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import com.example.newsaggregator.presentation.viewmodel.NewsViewModel

@Composable
fun NewsScreen(viewModel: NewsViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()

    LazyColumn {
        items(state.articles) { article ->
            NewsCard(article)
        }
    }
}