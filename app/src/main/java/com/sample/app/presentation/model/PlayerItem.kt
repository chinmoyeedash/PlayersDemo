package com.sample.app.presentation.model

import android.os.Parcelable
import com.sample.app.domain.models.IPlayer
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlayerInfo(
  val firstName: String?,
  val heightFeet: String?,
  val id: Int,
  val lastName: String?="",
  val position: String?,
  val teamName: String?,
  val weightPounds: String?,
  val imageUrl: String?,
  var showName: Boolean
): Parcelable