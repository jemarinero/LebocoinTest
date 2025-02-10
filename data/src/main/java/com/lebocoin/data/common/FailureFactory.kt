package com.lebocoin.data.common

import com.lebocoin.data.model.HttpCode
import com.lebocoin.domain.model.ErrorType
import com.lebocoin.domain.model.ResultOf
import okhttp3.ResponseBody
import java.net.SocketTimeoutException
import java.net.UnknownHostException

open class FailureFactory {
    open fun handleCode(code: Int, errorBody: ResponseBody?) =
        ResultOf.Failure(errorType = when (HttpCode.fromCode(code)) {
            HttpCode.NOT_FOUND,
            HttpCode.BAD_REQUEST -> ErrorType.ServiceError
            HttpCode.UNAUTHORIZED,
            HttpCode.FORBIDDEN -> ErrorType.SecurityError
            HttpCode.TIME_OUT,
            HttpCode.SERVER_ERROR ->  ErrorType.ServerError
            else -> ErrorType.UnknownError
        })

    open fun handleException(exception: Throwable) =
        ResultOf.Failure(errorType = when (exception) {
            is UnknownHostException, is SocketTimeoutException -> ErrorType.ConnectivityError
            else -> ErrorType.UnknownError
        })
}