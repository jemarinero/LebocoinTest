package com.lebocoin.domain.model

sealed class RequestFailure {
    data object ConnectivityError : RequestFailure()
    data object UnknownError : RequestFailure()
    data object ServiceError : RequestFailure()
    data object ServerError : RequestFailure()
    data object SecurityError : RequestFailure()
}