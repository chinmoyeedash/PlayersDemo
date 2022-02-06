package com.sample.app.domain.models


interface IPagingModel {
  val name: String?
  val id: Int
  val url: String?
  val width: Int
  val height: Int
  val boxCount: Int
}

interface IResponseModel {
  val data: IData
}

interface IData {
  val memes: List<IPagingModel>?
}