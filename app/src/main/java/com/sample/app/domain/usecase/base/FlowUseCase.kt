package com.sample.app.domain.usecase.base

import androidx.paging.PagingData
import com.sample.app.domain.exception.ApiError
import com.sample.app.domain.exception.getApiError
import com.sample.app.domain.models.IPlayer
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
/**
 * Simple use case exposing result as a flow.
 * Result flow will emit null while the action has not been triggered
 */
/*
@ExperimentalCoroutinesApi
abstract class FlowUseCase<in Params, Type>() where Type : Any {

  private val _trigger = MutableStateFlow(true)

  */
/**
   * Exposes result of this use case
   *//*

  val resultFlow: Flow<Type> = _trigger.flatMapLatest {
    run()
  }
  */
/**
   * Triggers the execution of this use case
   *//*

  suspend fun launch() {
    _trigger.emit(!(_trigger.value))
  }

  protected abstract suspend fun run(params: Params? = null) : Flow<Type>

}*/
