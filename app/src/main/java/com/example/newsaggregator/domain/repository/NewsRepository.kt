package com.example.newsaggregator.domain.repository

import com.example.newsaggregator.data.local.entities.ArticleEntity
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getTopNews(): List<ArticleEntity>
    fun getSavedNews(): Flow<List<ArticleEntity>>
}