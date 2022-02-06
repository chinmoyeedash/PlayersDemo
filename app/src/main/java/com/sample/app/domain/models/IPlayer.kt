package com.sample.app.domain.models


interface IPlayer {
  val firstName: String?
  val heightFeet: String?
  val heightInches: String?
  val id: Int
  val lastName: String?
  val position: String?
  val team: ITeam?
  val weightPounds: String?
  val imageUrl: String?
}