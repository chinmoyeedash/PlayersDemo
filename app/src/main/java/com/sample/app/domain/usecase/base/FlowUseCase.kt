package com.sample.app.domain.usecase.base

import com.sample.app.data.common.getApiError
import com.sample.app.domain.models.ApiError
import com.sample.app.domain.models.Either
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

abstract class FlowUseCase<in Params, out Type> where Type : Any? {

  abstract suspend fun run(params: Params): Flow<Type>

  operator fun invoke(
    scope: CoroutineScope,
    params: Params,
    onResult: (Flow<Type>) -> Unit = {},
    onFailure: (Flow<ApiError>) -> Unit = {},
    onCancelled: (CancellationException) -> Unit = {}
  ) {
    scope.launch {

      val successChannel = Channel<Type>()
      val errorChannel = Channel<ApiError>()

      onResult(successChannel.consumeAsFlow())
      onFailure(errorChannel.consumeAsFlow())

      run(params)
        .onEach {
          it?.let { successChannel.send(it) } ?: errorChannel.send(getApiError(null))
        }
        .catch { e ->

          // do not treat CancellationException as and error.
          if (e is CancellationException) {
            onCancelled.invoke(e)
          } else {
            errorChannel.send(getApiError(null))
          }
        }
        .collect()
    }
  }
}

