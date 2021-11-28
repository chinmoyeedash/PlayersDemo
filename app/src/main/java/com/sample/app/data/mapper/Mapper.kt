package com.sample.app.data.mapper

import android.text.TextUtils
import com.sample.app.data.models.PlayerInfo
import com.sample.app.data.models.PlayerResponse
import com.sample.app.data.models.Team
import com.sample.app.domain.models.IPlayer

fun PlayerResponse.toPlayerData(): List<IPlayer> {
  val players = mutableListOf<IPlayer>()
  data?.forEach { player ->
    val item = PlayerInfo(
      id = player.id,
      firstName = player.first_name,
      lastName = player.last_name,
      imageUrl = getPlayerImageUrl(player.first_name, player.last_name),
      heightFeet = player.height_feet,
      heightInches = player.height_inches,
      weightPounds = player.weight_pounds,
      team = Team(
        id = player.team?.id,
        fullName = player.team?.full_name,
        abbreviation = player.team?.abbreviation,
        division = player.team?.division,
        city = player.team?.city,
      ),
      position = player.position
    )
    players.add(item)
  }
  return players
}

private fun getPlayerImageUrl(firstName: String?="", lastName: String?=""): String? {
  return if (!TextUtils.isEmpty(firstName) && !TextUtils.isEmpty(lastName)) "https://nba-players.herokuapp.com/players/$lastName/$firstName" else null
}