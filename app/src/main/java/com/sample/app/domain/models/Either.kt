package com.sample.app.domain.models


/**
 * A generic class that holds a value with its loading status.
 *
 * Result is usually created by the Repository classes where they return
 * `LiveData<Result<T>>` to pass back the latest data to the UI with its fetch status.
 */

/*sealed class Either<out T> {
    data class Success<out T>(val data: T) : Either<T>()
    data class ApiError<out T>(val message: String?, val code: Int?=null, var errorStatus: ErrorStatus) : Either<T>()

    fun either(response: (T) -> Any): Any =
        when (this) {
            is ApiError -> response(data)
            is Success -> response(successVal)
        }
}*/
sealed class Either<out L, out R> where R : Any? {

    data class Error<out L>(val errorVal: L) : Either<L, Nothing>()

    data class Success<out R>(val successVal: R) : Either<Nothing, R>()

    val isError get() = this is Error<L>

    val isSuccess get() = this is Success<R>

    fun either(fnL: (L) -> Any, fnR: (R) -> Any): Any =
        when (this) {
            is Error -> fnL(errorVal)
            is Success -> fnR(successVal)
        }

    fun errorValue() = if (this is Error) errorVal else null

    fun successValue() = if (this is Success) successVal else null
}

data class ApiError(val message: String?, val code: Int?=null, var errorStatus: ErrorStatus)

    enum class ErrorStatus {
        /**
         * Any case where a parameter is invalid, or a required parameter is missing.
         * This includes the case where no OAuth token is provided and
         * the case where a resource ID is specified incorrectly in a path.
         */
        BAD_REQUEST,

        /**
         * The OAuth token was provided but was invalid.
         */
        UNAUTHORIZED,

        /**
         * The requested information cannot be viewed by the acting user, for example,
         * because they are not friends with the user whose data they are trying to read.
         * It could also indicate privileges or access has been revoked.
         */
        FORBIDDEN,

        /**
         * Endpoint does not exist.
         */
        NOT_FOUND,

        /**
         * Attempting to use POST with a GET-only endpoint, or vice-versa.
         */
        METHOD_NOT_ALLOWED,

        /**
         * The request could not be completed as it is. Use the information included in the response to modify the request and retry.
         */
        CONFLICT,

        /**
         * There is either a bug on our side or there is an outage.
         * The request is probably valid but needs to be retried later.
         */
        INTERNAL_SERVER_ERROR,

        /**
         * Time out  error
         */
        TIMEOUT,

        /**
         * Error in connecting to repository (Server or Database)
         */
        NO_CONNECTION,

        /**
         * When error is not known
         */
        UNKNOWN_ERROR
    }
