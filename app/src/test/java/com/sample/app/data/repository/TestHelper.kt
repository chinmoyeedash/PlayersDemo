package com.sample.app.data.repository

import com.sample.app.data.models.Data
import com.sample.app.data.models.PagingModel
import com.sample.app.data.models.ResponseModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import retrofit2.Response

class TestHelper {

  companion object {
    internal fun getMemesData(): ResponseModel {
      return ResponseModel(
          data = Data(
            memes =
            arrayListOf(
              PagingModel(
                id = 1,
                name = "abc",
                url = "sfs",
                width = 1,
                height = 1,
                boxCount = 1
              )
            )
          )
        )
    }

    internal inline fun <reified T> getApiError(): HttpException {
      val error = Response.error<T>(
        400,
        ("{\"status\":\"failed\", \"title\":\" api\",\"display_message\":\"DEFAULT_ERROR_MSG\",\"data\":\"null\"}").toResponseBody(
          "application/json".toMediaTypeOrNull()
        )
      )
      return HttpException(error)
    }
  }
}