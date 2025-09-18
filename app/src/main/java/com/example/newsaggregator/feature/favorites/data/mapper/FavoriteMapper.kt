package com.example.newsaggregator.feature.favorites.data.mapper

import com.example.newsaggregator.feature.favorites.data.local.entity.FavoriteEntity
import com.example.newsaggregator.feature.newsfeed.domain.model.NewsItem
import javax.inject.Inject

class FavoriteMapper @Inject constructor() {
    fun domainToEntity(item: NewsItem, savedAt: Long): FavoriteEntity {
        return FavoriteEntity(
            newsUrl = item.url,
            savedAt = savedAt
        )
    }
}