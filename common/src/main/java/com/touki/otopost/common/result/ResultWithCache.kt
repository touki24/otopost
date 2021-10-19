package com.touki.otopost.common.result

sealed class ResultWithCache <out T> {
    data class Success<T>(val data: T): ResultWithCache<T>()
    data class Failure<T>(val error: Error, val cache: T): ResultWithCache<T>()

    inline fun <V> fold(success: (T) -> V, failure: (Error, T) -> V): V = when (this) {
        is Success -> success(this.data)
        is Failure -> failure(this.error, this.cache)
    }
}