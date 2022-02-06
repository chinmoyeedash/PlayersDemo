package com.sample.app.domain.usecase

import com.sample.app.domain.models.ApiError
import com.sample.app.domain.models.Either
import com.sample.app.domain.models.IResponseModel
import com.sample.app.domain.repository.ISampleRepository
import com.sample.app.domain.usecase.base.UseCase


class GetMemesUseCase constructor(
  private val playerRepository: ISampleRepository
) : UseCase<Unit, IResponseModel, >() {
  override suspend fun run(params:Unit): Either<ApiError, IResponseModel> {
    return playerRepository.getMemes()
  }
}