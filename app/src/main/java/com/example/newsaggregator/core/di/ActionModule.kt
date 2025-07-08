package com.example.newsaggregator.core.di

import com.example.newsaggregator.feature.newsfeed.data.action.FetchHeadlinesActionImpl
import com.example.newsaggregator.feature.newsfeed.data.action.GetSourcesActionImpl
import com.example.newsaggregator.feature.newsfeed.data.action.SearchNewsActionImpl
import com.example.newsaggregator.feature.newsfeed.data.local.dao.NewsDao
import com.example.newsaggregator.feature.newsfeed.data.local.dao.SourcesDao
import com.example.newsaggregator.feature.newsfeed.data.mapper.NewsMapper
import com.example.newsaggregator.feature.newsfeed.data.mapper.SourceMapper
import com.example.newsaggregator.feature.newsfeed.data.remote.api.NewsApi
import com.example.newsaggregator.feature.newsfeed.domain.action.FetchHeadlinesAction
import com.example.newsaggregator.feature.newsfeed.domain.action.GetSourcesAction
import com.example.newsaggregator.feature.newsfeed.domain.action.SearchNewsAction
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ActionModule {

    @Provides
    fun provideFetchHeadlinesAction(
        api: NewsApi,
        dao: NewsDao,
        mapper: NewsMapper
    ): FetchHeadlinesAction = FetchHeadlinesActionImpl(api, dao, mapper)

    @Provides
    fun provideGetSourcesAction(
        api: NewsApi,
        dao: SourcesDao,
        mapper: SourceMapper
    ): GetSourcesAction = GetSourcesActionImpl(api, dao, mapper)

    @Provides
    fun provideSearchNewsAction(
        api: NewsApi,
        dao: NewsDao,
        mapper: NewsMapper
    ): SearchNewsAction = SearchNewsActionImpl(api, dao, mapper)

    @Provides
    fun provideNewsMapper(): NewsMapper = NewsMapper()

    @Provides
    fun provideSourceMapper(): SourceMapper = SourceMapper()
}