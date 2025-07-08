package com.example.newsaggregator.feature.newsfeed.domain.usecase

import com.example.newsaggregator.feature.newsfeed.domain.action.FetchHeadlinesAction
import com.example.newsaggregator.feature.newsfeed.domain.model.NewsItem
import javax.inject.Inject

class GetHeadlinesUseCase @Inject constructor(
    private val fetchHeadlines: FetchHeadlinesAction
) {
    suspend operator fun invoke(
        country: String = "us",
        page: Int = 1
    ): Result<List<NewsItem>> {
        return fetchHeadlines(country, page)
    }
}