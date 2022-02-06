package com.sample.app.data.mapper

import com.sample.app.domain.models.Either
import com.sample.app.domain.models.ErrorStatus
import com.sample.app.data.common.getApiError
import com.sample.app.data.models.ResponseModel
import com.sample.app.domain.models.ApiError
import com.sample.app.domain.models.IPagingModel
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import retrofit2.Response


object Mapper {

  suspend fun <T> getResult(call: suspend () -> Response<T>): Either<ApiError, T> {


      try {
        val response = call()
        if (response.isSuccessful) {
          val body = response.body()
          if (body != null)  return Either.Success(body)
        }
        return Either.Error(
          ApiError(
            message = response.message(),
            code = response.code(),
            errorStatus = ErrorStatus.UNKNOWN_ERROR
          )
        )
      } catch (e: Exception) {
        return Either.Error(getApiError(e))
      }
    }


}
