package com.lebocoin.data.common

import com.lebocoin.data.model.HttpCode
import com.lebocoin.domain.model.RequestFailure
import com.lebocoin.domain.model.ResultOf
import okhttp3.ResponseBody
import java.net.SocketTimeoutException
import java.net.UnknownHostException

open class FailureFactory {
    open fun handleCode(code: Int, errorBody: ResponseBody?) =
        ResultOf.Failure(requestFailure = when (HttpCode.fromCode(code)) {
            HttpCode.NOT_FOUND,
            HttpCode.BAD_REQUEST -> RequestFailure.ServiceError
            HttpCode.UNAUTHORIZED,
            HttpCode.FORBIDDEN -> RequestFailure.SecurityError
            HttpCode.TIME_OUT,
            HttpCode.SERVER_ERROR ->  RequestFailure.ServerError
            else -> RequestFailure.UnknownError
        })

    open fun handleException(exception: Throwable) =
        ResultOf.Failure(requestFailure = when (exception) {
            is UnknownHostException, is SocketTimeoutException -> RequestFailure.ConnectivityError
            else -> RequestFailure.UnknownError
        })
}