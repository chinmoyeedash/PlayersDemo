package com.sample.app.domain.repository

import androidx.paging.PagingData
import com.sample.app.data.models.PlayerResponse
import com.sample.app.domain.models.IPlayer
import kotlinx.coroutines.flow.Flow

interface IPlayersRepository {

  suspend fun getPlayersFlow(): Flow<PagingData<IPlayer>>

  suspend fun getPlayersInfo(): List<IPlayer>
}