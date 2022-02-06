package com.sample.app.data.common

import com.sample.app.domain.models.ApiError
import com.sample.app.domain.models.Either
import com.sample.app.domain.models.ErrorStatus
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception
import java.net.SocketTimeoutException

/**
 * Trace exceptions(api call or parse data or connection errors) &
 * depending on what exception returns [ApiErrorf]
 *
 * */
fun getApiError(throwable: Exception?): ApiError {
    val UNKNOWN_ERROR_MESSAGE = "Unknown Error"
    return when (throwable) {

        is HttpException -> {

            when (throwable.code()) {
                400 -> ApiError(
                    message = throwable.message(),
                    code = throwable.code(),
                    errorStatus = ErrorStatus.BAD_REQUEST
                )
                401 -> ApiError(
                    throwable.message(),
                    throwable.code(),
                    ErrorStatus.UNAUTHORIZED
                )
                403 -> ApiError(
                    throwable.message(),
                    throwable.code(),
                    ErrorStatus.FORBIDDEN
                )
                404 ->  ApiError(
                    throwable.message(),
                    throwable.code(),
                    ErrorStatus.NOT_FOUND
                )
                405 ->  ApiError(
                    throwable.message(),
                    throwable.code(),
                    ErrorStatus.METHOD_NOT_ALLOWED
                )
                409 ->  ApiError(
                    throwable.message(),
                    throwable.code(),
                    ErrorStatus.CONFLICT
                )
                500 ->  ApiError(
                    throwable.message(),
                    throwable.code(),
                    ErrorStatus.INTERNAL_SERVER_ERROR
                )
                else ->  ApiError(
                    UNKNOWN_ERROR_MESSAGE,
                    0,
                    ErrorStatus.UNKNOWN_ERROR
                )
            }
        }

        is SocketTimeoutException -> {
            ApiError(message = throwable.message, errorStatus = ErrorStatus.TIMEOUT)
        }

        is IOException -> {
            ApiError(message = throwable.message, errorStatus = ErrorStatus.NO_CONNECTION)
        }

        else ->  ApiError(UNKNOWN_ERROR_MESSAGE, 0, ErrorStatus.UNKNOWN_ERROR)
    }
}