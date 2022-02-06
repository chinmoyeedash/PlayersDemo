package com.sample.app.data.models

import com.google.gson.annotations.SerializedName
import com.sample.app.domain.models.IData
import com.sample.app.domain.models.IPagingModel
import com.sample.app.domain.models.IResponseModel

data class ResponseModel(
  @SerializedName("data")
  override val data: Data
): IResponseModel

data class Data(
  @SerializedName("memes")
  override val memes:List<PagingModel>?
): IData

data class PagingModel (
  @SerializedName("id")
  override val id: Int,
  @SerializedName("name")
  override val name: String?,
  @SerializedName("url")
  override val url: String?,
  @SerializedName("width")
  override val width: Int,
  @SerializedName("height")
  override val height: Int,
  @SerializedName("box_count")
  override val boxCount: Int,

  ): IPagingModel
