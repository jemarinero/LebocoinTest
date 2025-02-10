package com.lebocoin.domain.model

sealed class ErrorType {
    data object ConnectivityError : ErrorType()
    data object UnknownError : ErrorType()
    data object ServiceError : ErrorType()
    data object ServerError : ErrorType()
    data object SecurityError : ErrorType()
    data object EmptyError : ErrorType()
}