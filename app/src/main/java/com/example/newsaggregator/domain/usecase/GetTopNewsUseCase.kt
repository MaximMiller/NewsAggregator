package com.example.newsaggregator.domain.usecase

import com.example.newsaggregator.data.local.entities.ArticleEntity
import com.example.newsaggregator.domain.repository.NewsRepository

class GetTopNewsUseCase(
    private val repository: NewsRepository
) {
    suspend operator fun invoke(): List<ArticleEntity> {
        return repository.getTopNews()
    }
}