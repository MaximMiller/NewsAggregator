package com.example.newsaggregator.core.util

import androidx.compose.runtime.Immutable

/**
 * Immutable wrapper for List to make it stable in Compose
 * @param items wrapped list
 */
@Immutable
data class ImmutableList<T>(val items: List<T>) : List<T> by items {
    constructor() : this(emptyList())
}

/**
 * Converts List to ImmutableList
 */
fun <T> List<T>.toImmutableList() = ImmutableList(this)