package com.sample.app.domain.usecase.base

import androidx.paging.PagingData
import com.sample.app.data.repository.PlayerRepository
import com.sample.app.domain.models.IPlayer
import com.sample.app.domain.repository.IPlayersRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow


@ExperimentalCoroutinesApi
class GetPlayersFlowUseCase constructor(
  private val playerRepository: IPlayersRepository
) : FlowUseCase<Unit, PagingData<IPlayer>>() {
  override suspend fun run(params: Unit): Flow<PagingData<IPlayer>> {
    return playerRepository.getPlayersFlow()
  }
}
