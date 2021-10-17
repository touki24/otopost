package com.touki.otopost.common.result

sealed class CommonResult<out T> {
    data class Success<T>(val data: T): CommonResult<T>()
    data class Failure<T>(val error: Error): CommonResult<T>()

    inline fun <V> fold(success: (T) -> V, failure: (Error) -> V): V = when (this) {
        is Success -> success(this.data)
        is Failure -> failure(this.error)
    }
}