package com.example.newsaggregator.feature.newsfeed.domain.model

data class SourceItem(
    val id: String,
    val name: String,
    val description: String,
    val category: String,
    val language: String,
    val country: String
)
