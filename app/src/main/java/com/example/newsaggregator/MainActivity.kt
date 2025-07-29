package com.example.newsaggregator

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

import com.example.newsaggregator.feature.newsfeed.presentation.screen.feed.NewsFeedScreen
import com.example.newsaggregator.ui.theme.NewsAggregatorTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsAggregatorTheme {
                NewsFeedScreen(onNewsClick = { id ->
                    // Пока просто логируем клик
                    Log.d("MainActivity", "Clicked news id: $id")
                }
                )
            }
        }
    }
}
