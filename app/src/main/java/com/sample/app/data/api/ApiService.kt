package com.sample.app.data.api

import com.sample.app.data.models.PlayerResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

  /*@GET("/players-stats")
  suspend fun getPlayerStats(): List<Player>*/


  @GET("players")
  suspend fun getPlayers(@Query("page") pageIndex: Int): PlayerResponse
}