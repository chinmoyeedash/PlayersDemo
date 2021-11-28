package com.sample.app.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sample.app.data.api.ApiService
import com.sample.app.data.mapper.toPlayerData
import com.sample.app.domain.models.IPlayer

class PlayerDataSource(val service: ApiService):  PagingSource<Int, IPlayer>()  {
  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, IPlayer> {
    return try {
      // Start refresh at page 1 if undefined.
      val nextPage = params.key ?: 1
      val response = service.getPlayers(nextPage)

      LoadResult.Page(
        data = response.toPlayerData(),
        prevKey = if (nextPage == 1) null else nextPage - 1,
        nextKey = response.meta?.next_page
      )
    } catch (e: Exception) {
      LoadResult.Error(e)
    }
  }

  override fun getRefreshKey(state: PagingState<Int, IPlayer>): Int? {
    return state.anchorPosition?.let { anchorPosition ->
      state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
        ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
    }
  }
}