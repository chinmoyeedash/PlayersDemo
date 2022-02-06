
package com.sample.app.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sample.app.data.api.ApiService
import com.sample.app.domain.models.IPagingModel
import java.lang.Exception

class MyPagingDataSource(val service: ApiService):  PagingSource<Int, IPagingModel>()  {
  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, IPagingModel> {
    return LoadResult.Error(Exception())
    /*return try {
      // Start refresh at page 1 if undefined.
      val nextPage = params.key ?: 1
      val response = service.getMemes(nextPage)

      LoadResult.Page(
        data = response,
        prevKey = if (nextPage == 1) null else nextPage - 1,
        nextKey = response.next_page
      )
    } catch (e: Exception) {
      LoadResult.Error(e)
    }*/
  }

  override fun getRefreshKey(state: PagingState<Int, IPagingModel>): Int? {
    return state.anchorPosition?.let { anchorPosition ->
      state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
        ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
    }
  }
}
