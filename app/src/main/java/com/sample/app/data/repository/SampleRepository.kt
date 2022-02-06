package com.sample.app.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sample.app.data.api.ApiService
import com.sample.app.domain.models.Either
import com.sample.app.data.mapper.Mapper.getResult
import com.sample.app.domain.models.ApiError
import com.sample.app.domain.models.IPagingModel
import com.sample.app.domain.models.IResponseModel
import com.sample.app.domain.repository.ISampleRepository
import kotlinx.coroutines.flow.Flow

private const val NETWORK_PAGE_SIZE: Int = 25

class SampleRepository(private val service: ApiService) : ISampleRepository {

  override suspend fun getMemes(): Either<ApiError, IResponseModel> {
    return getResult{service.getMemes()}
  }

  override suspend fun getMemesFlow(): Flow<PagingData<IPagingModel>> {
    return Pager(
      config = PagingConfig(
        pageSize = NETWORK_PAGE_SIZE,
        enablePlaceholders = false
      ),
      pagingSourceFactory = {
        MyPagingDataSource(service = service)
      }
    ).flow
  }



}
