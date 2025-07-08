package com.example.newsaggregator.feature.newsfeed.data.remote.api

import com.example.newsaggregator.feature.newsfeed.data.remote.dto.NewsResponseDto
import com.example.newsaggregator.feature.newsfeed.data.remote.dto.SourcesResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String? = null,
        @Query("category") category: String? = null,
        @Query("sources") sources: String? = null,
        @Query("q") query: String? = null,
        @Query("pageSize") pageSize: Int? = null,
        @Query("page") page: Int? = null
    ): Response<NewsResponseDto>

    @GET("everything")
    suspend fun searchNews(
        @Query("q") query: String,
        @Query("sources") sources: String? = null,
        @Query("domains") domains: String? = null,
        @Query("excludeDomains") excludeDomains: String? = null,
        @Query("from") fromDate: String? = null,
        @Query("to") toDate: String? = null,
        @Query("language") language: String? = null,
        @Query("sortBy") sortBy: String? = null,
        @Query("pageSize") pageSize: Int? = null,
        @Query("page") page: Int? = null
    ): Response<NewsResponseDto>

    @GET("sources")
    suspend fun getSources(
        @Query("category") category: String? = null,
        @Query("language") language: String? = null,
        @Query("country") country: String? = null
    ): Response<SourcesResponseDto>
}
