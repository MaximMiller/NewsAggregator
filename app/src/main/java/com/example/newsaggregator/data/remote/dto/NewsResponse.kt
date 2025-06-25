package com.example.newsaggregator.data.remote.dto

import com.squareup.moshi.Json

data class NewsResponse(
    @Json(name = "status") val status: String,
    @Json(name = "totalResults") val totalResults: Int,
    @Json(name = "articles") val articles: List<ArticleDto>
)

data class SourceDto(
    @Json(name = "id") val id: String?,
    @Json(name = "name") val name: String
)