package com.example.newsaggregator.core.network.extensions

import com.example.newsaggregator.core.network.exceptions.NullBodyException
import retrofit2.Response

fun <T> Response<T>.toResult(): Result<T> {
    return if (isSuccessful) {
        body()?.let { Result.success(it) }
            ?: Result.failure(NullBodyException())
    } else {
        Result.failure(
            com.example.newsaggregator.core.network.exceptions.ApiException(
                code = code(),
                errorMessage = errorBody()?.string()
            )
        )
    }
}