package com.example.newsaggregator.feature.newsfeed.domain.model

data class NewsItem(
    val id: String,
    val title: String,
    val description: String?,
    val url: String,
    val imageUrl: String?,
    val publishedAt: String,
    val source: String,
    val isFavorite: Boolean
)