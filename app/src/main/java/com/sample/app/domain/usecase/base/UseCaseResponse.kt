package com.sample.app.domain.usecase.base

import com.sample.app.domain.exception.ApiError

interface UseCaseResponse<Type> {

    fun onSuccess(result: Type)

    fun onError(apiError: ApiError?)
}

