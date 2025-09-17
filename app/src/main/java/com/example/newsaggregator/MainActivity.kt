package com.example.newsaggregator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newsaggregator.feature.favorites.presentation.screen.FavoritesScreen
import com.example.newsaggregator.feature.newsfeed.presentation.screen.detail.NewsDetailScreen
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
                NavigationApp()
            }
        }
    }
}

@Composable
fun NavigationApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "newsFeed"
    ) {
        composable("newsFeed") {
            NewsFeedScreen(
                onNewsClick = { newsId ->
                    navController.navigate("newsDetail/$newsId")
                },
                onFavoritesClick = {
                    navController.navigate("favorites")
                }
            )
        }
        composable("newsDetail/{newsId}") { backStackEntry ->
            val newsId = backStackEntry.arguments?.getString("newsId")?.toLongOrNull() ?: 0
            NewsDetailScreen(
                newsId = newsId,
                onBackClick = { navController.popBackStack() }
            )
        }
        composable("favorites") {
            FavoritesScreen(
                onBackClick = { navController.popBackStack() },
                onNewsClick = { newsId ->
                    navController.navigate("newsDetail/$newsId")
                }
            )
        }
    }
}