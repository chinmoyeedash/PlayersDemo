package com.sample.app.domain.usecase.base

import com.sample.app.domain.models.ApiError
import com.sample.app.domain.models.Either
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

abstract class UseCase<in Params, Type>() where Type : Any {

  abstract suspend fun run(params: Params): Either<ApiError, Type>

  operator fun invoke(
    scope: CoroutineScope,
    params: Params,
    onSuccess: (Type) -> Unit = {},
    onFailure: (ApiError) -> Unit = {}
  ) {
    val job = scope.async { run(params) }
    scope.launch {
      job.await().either(onFailure, onSuccess)
    }
  }


}

