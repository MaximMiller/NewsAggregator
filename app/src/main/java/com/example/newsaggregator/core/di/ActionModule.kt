package com.example.newsaggregator.core.di

import com.example.newsaggregator.feature.favorites.data.action.IsFavoriteCheckActionImpl
import com.example.newsaggregator.feature.favorites.data.action.LoadFavoritesActionImpl
import com.example.newsaggregator.feature.favorites.data.action.RemoveFavoriteActionImpl
import com.example.newsaggregator.feature.favorites.data.action.SaveFavoriteActionImpl
import com.example.newsaggregator.feature.favorites.data.local.dao.FavoritesDao
import com.example.newsaggregator.feature.favorites.data.mapper.FavoriteMapper
import com.example.newsaggregator.feature.favorites.domain.action.IsFavoriteCheckAction
import com.example.newsaggregator.feature.favorites.domain.action.LoadFavoritesAction
import com.example.newsaggregator.feature.favorites.domain.action.RemoveFavoriteAction
import com.example.newsaggregator.feature.favorites.domain.action.SaveFavoriteAction
import com.example.newsaggregator.feature.newsfeed.data.action.FetchHeadlinesActionImpl
import com.example.newsaggregator.feature.newsfeed.data.action.GetSourcesActionImpl
import com.example.newsaggregator.feature.newsfeed.data.action.SearchNewsActionImpl
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
        mapper: NewsMapper
    ): FetchHeadlinesAction = FetchHeadlinesActionImpl(api, mapper)

    @Provides
    fun provideGetSourcesAction(
        api: NewsApi,
        mapper: SourceMapper
    ): GetSourcesAction = GetSourcesActionImpl(api, mapper)

    @Provides
    fun provideSearchNewsAction(
        api: NewsApi,
        mapper: NewsMapper
    ): SearchNewsAction = SearchNewsActionImpl(api, mapper)

    @Provides
    fun provideSaveFavoriteAction(
        favoritesDao: FavoritesDao,
        mapper: FavoriteMapper
    ): SaveFavoriteAction = SaveFavoriteActionImpl(favoritesDao, mapper)

    @Provides
    fun provideRemoveFavoriteAction(
        favoritesDao: FavoritesDao,
    ): RemoveFavoriteAction = RemoveFavoriteActionImpl(favoritesDao)

    @Provides
    fun provideLoadFavoritesAction(
        favoritesDao: FavoritesDao,
        newsMapper: NewsMapper
    ): LoadFavoritesAction = LoadFavoritesActionImpl(favoritesDao, newsMapper)

    @Provides
    fun provideIsFavoriteCheckAction(
        favoritesDao: FavoritesDao
    ): IsFavoriteCheckAction = IsFavoriteCheckActionImpl(favoritesDao)

    @Provides
    fun provideNewsMapper(): NewsMapper = NewsMapper()

    @Provides
    fun provideSourceMapper(): SourceMapper = SourceMapper()

    @Provides
    fun provideFavoriteMapper(): FavoriteMapper = FavoriteMapper()
}