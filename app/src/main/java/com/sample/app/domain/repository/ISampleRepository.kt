package com.sample.app.domain.repository

import androidx.paging.PagingData
import com.sample.app.domain.models.ApiError
import com.sample.app.domain.models.Either
import com.sample.app.domain.models.IPagingModel
import com.sample.app.domain.models.IResponseModel
import kotlinx.coroutines.flow.Flow

interface ISampleRepository {

  suspend fun getMemesFlow(): Flow<PagingData<IPagingModel>>

  suspend fun getMemes(): Either<ApiError, IResponseModel>
}