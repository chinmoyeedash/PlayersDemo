package com.sample.app.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sample.app.data.api.ApiService
import com.sample.app.data.mapper.toPlayerData
import com.sample.app.domain.models.IPlayer
import com.sample.app.domain.repository.IPlayersRepository
import kotlinx.coroutines.flow.Flow

private const val NETWORK_PAGE_SIZE: Int = 25

class PlayerRepository(private val service: ApiService) : IPlayersRepository {

   override suspend fun getPlayersInfo(): List<IPlayer> {
     return service.getPlayers(2).toPlayerData()
   }
  override suspend fun getPlayersFlow(): Flow<PagingData<IPlayer>> {
    return Pager(
      config = PagingConfig(
        pageSize = NETWORK_PAGE_SIZE,
        enablePlaceholders = false
      ),
      pagingSourceFactory = {
        PlayerDataSource(service = service)
      }
    ).flow
  }


}
