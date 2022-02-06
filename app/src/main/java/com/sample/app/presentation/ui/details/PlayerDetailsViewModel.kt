package com.sample.app.presentation.ui.details

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.sample.app.presentation.model.PlayerInfo

class PlayerDetailsViewModel : ViewModel() {

  val playerInfo = ObservableField<PlayerInfo>()
  fun setPlayerDetails(player: PlayerInfo?) {
    player?.let { playerInfo.set(it) }
  }

}