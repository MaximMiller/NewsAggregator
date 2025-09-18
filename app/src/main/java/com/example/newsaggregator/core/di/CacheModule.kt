package com.example.newsaggregator.core.di

import com.example.newsaggregator.feature.favorites.domain.action.IsFavoriteCheckAction
import com.example.newsaggregator.feature.newsfeed.data.action.GetCachedHeadlinesImpl
import com.example.newsaggregator.feature.newsfeed.data.action.GetCachedNewsByQueryImpl
import com.example.newsaggregator.feature.newsfeed.data.action.GetCachedSourcesByFiltersImpl
import com.example.newsaggregator.feature.newsfeed.data.action.SaveNewsToCacheImpl
import com.example.newsaggregator.feature.newsfeed.data.action.SaveSourcesToCacheImpl
import com.example.newsaggregator.feature.newsfeed.data.local.dao.NewsDao
import com.example.newsaggregator.feature.newsfeed.data.local.dao.SourcesDao
import com.example.newsaggregator.feature.newsfeed.data.mapper.NewsMapper
import com.example.newsaggregator.feature.newsfeed.data.mapper.SourceMapper
import com.example.newsaggregator.feature.newsfeed.domain.action.GetCachedHeadlinesAction
import com.example.newsaggregator.feature.newsfeed.domain.action.GetCachedHeadlinesByCategoryAction
import com.example.newsaggregator.feature.newsfeed.domain.action.GetCachedNewsByQueryAction
import com.example.newsaggregator.feature.newsfeed.domain.action.GetCachedSourcesByFiltersAction
import com.example.newsaggregator.feature.newsfeed.domain.action.SaveCategoryNewsToCacheAction
import com.example.newsaggregator.feature.newsfeed.domain.action.SaveNewsToCacheAction
import com.example.newsaggregator.feature.newsfeed.domain.action.SaveSourcesToCacheAction
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * TODO: Реализовать feature-модули для DI (разделение по фичам)
 *
 * Планируется:
 * 1. Создать отдельные модули для каждой фичи
 * 2. Изучить Dagger Binding для:
 *    - Упрощения привязки интерфейсов к реализациям
 *    - Использования Multibinding для коллекций зависимостей
 *
 * @see dagger.Binds
 * @see dagger.multibindings.Multibinds
 *
 */

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    @Provides
    fun provideSaveNewsToCacheAction(
        dao: NewsDao,
        mapper: NewsMapper,
        isFavoriteCheckAction: IsFavoriteCheckAction
    ): SaveNewsToCacheAction = SaveNewsToCacheImpl(dao, mapper, isFavoriteCheckAction)

    @Provides
    fun provideSaveCategoryNewsToCacheAction(
        dao: NewsDao,
        mapper: NewsMapper,
        isFavoriteCheckAction: IsFavoriteCheckAction
    ): SaveCategoryNewsToCacheAction = SaveNewsToCacheImpl(dao, mapper, isFavoriteCheckAction)

    @Provides
    fun provideGetCachedNewsByQueryAction(
        dao: NewsDao,
        mapper: NewsMapper
    ): GetCachedNewsByQueryAction = GetCachedNewsByQueryImpl(dao, mapper)

    @Provides
    fun provideGetCachedHeadlinesAction(
        dao: NewsDao,
        mapper: NewsMapper
    ): GetCachedHeadlinesAction = GetCachedHeadlinesImpl(dao, mapper)

    @Provides
    fun provideGetCachedHeadlinesByCategoryAction(
        dao: NewsDao,
        mapper: NewsMapper
    ): GetCachedHeadlinesByCategoryAction = GetCachedHeadlinesImpl(dao, mapper)

    @Provides
    fun provideSaveSourcesToCacheAction(
        dao: SourcesDao,
        mapper: SourceMapper
    ): SaveSourcesToCacheAction = SaveSourcesToCacheImpl(dao, mapper)

    @Provides
    fun provideGetCachedSourcesByFiltersAction(
        dao: SourcesDao,
        mapper: SourceMapper
    ): GetCachedSourcesByFiltersAction = GetCachedSourcesByFiltersImpl(dao, mapper)
}