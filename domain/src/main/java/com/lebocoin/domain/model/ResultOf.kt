package com.lebocoin.domain.model

sealed class ResultOf<out T> {
    data class Success<out R>(val value: R): ResultOf<R>()
    data class Failure(val requestFailure: RequestFailure): ResultOf<Nothing>()
}

inline fun <reified T> ResultOf<T>.doIfFailure(callback: (failure: RequestFailure?) -> Unit) {
    if (this is ResultOf.Failure) {
        callback(requestFailure)
    }
}

inline fun <reified T> ResultOf<T>.doIfSuccess(callback: (value: T) -> Unit) {
    if (this is ResultOf.Success) {
        callback(value)
    }
}