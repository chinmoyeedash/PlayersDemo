package com.sample.app.data.api

import com.sample.app.data.models.ResponseModel
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
  @GET("get_memes")
  suspend fun getMemes(): Response<ResponseModel>
}