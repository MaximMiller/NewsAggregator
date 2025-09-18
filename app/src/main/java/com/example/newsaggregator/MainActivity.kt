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
import java.net.URLDecoder
import java.net.URLEncoder

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
                onNewsClick = { newsUrl ->
                    val encodedUrl = URLEncoder.encode(newsUrl, "UTF-8")
                    navController.navigate("newsDetail/$encodedUrl")
                },
                onFavoritesClick = {
                    navController.navigate("favorites")
                }
            )
        }
        composable("newsDetail/{newsUrl}") { backStackEntry ->
            val encodedUrl = backStackEntry.arguments?.getString("newsUrl") ?: ""
            val newsUrl = URLDecoder.decode(encodedUrl, "UTF-8")
            NewsDetailScreen(
                newsUrl = newsUrl,
                onBackClick = { navController.popBackStack() }
            )
        }
        composable("favorites") {
            FavoritesScreen(
                onBackClick = { navController.popBackStack() },
                onNewsClick = { newsUrl ->
                    val encodedUrl = URLEncoder.encode(newsUrl, "UTF-8")
                    navController.navigate("newsDetail/$encodedUrl")
                }
            )
        }
    }
}