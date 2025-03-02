package com.lebocoin.data.model

enum class HttpCode(val code: Int) {
    OK(200),
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    FORBIDDEN(403),
    NOT_FOUND(404),
    SERVER_ERROR(500),
    TIME_OUT(503);

    companion object {
        fun fromCode(code: Int) = entries.firstOrNull { c -> c.code == code } ?: SERVER_ERROR
    }
}