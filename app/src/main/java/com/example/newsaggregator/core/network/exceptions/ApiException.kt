package com.example.newsaggregator.core.network.exceptions

class ApiException(
    val code: Int,
    val errorMessage: String?
) : Exception("API Error $code: ${errorMessage ?: "No message"}")

class NullBodyException : Exception("Response body is null")