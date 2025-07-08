package com.example.newsaggregator.feature.newsfeed.data.remote.dto

import com.google.gson.annotations.SerializedName

data class NewsResponseDto(
    @SerializedName("status") val status: String,
    @SerializedName("totalResults") val totalResults: Int,
    @SerializedName("articles") val articles: List<ArticleDto>,
    @SerializedName("code") val errorCode: String? = null,
    @SerializedName("message") val errorMessage: String? = null
)