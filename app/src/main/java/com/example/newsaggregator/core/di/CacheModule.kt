package com.example.newsaggregator.core.di

import com.example.newsaggregator.feature.newsfeed.data.local.cache.CacheNewsAction
import com.example.newsaggregator.feature.newsfeed.data.local.cache.CacheSourcesAction
import com.example.newsaggregator.feature.newsfeed.data.local.dao.NewsDao
import com.example.newsaggregator.feature.newsfeed.data.local.dao.SourcesDao
import com.example.newsaggregator.feature.newsfeed.data.mapper.NewsMapper
import com.example.newsaggregator.feature.newsfeed.data.mapper.SourceMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {
    @Provides
    fun provideCacheNewsAction(
        dao: NewsDao,
        mapper: NewsMapper
    ): CacheNewsAction = CacheNewsAction(dao, mapper)

    @Provides
    fun provideCacheSourcesAction(
        dao: SourcesDao,
        mapper: SourceMapper
    ): CacheSourcesAction = CacheSourcesAction(dao, mapper)
}