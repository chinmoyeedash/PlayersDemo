package com.sample.app.presentation.ui.home.placeholder

import androidx.recyclerview.widget.DiffUtil
import com.sample.app.presentation.model.PlayerInfo

class PlayersDiffCallback(
  private val mOldList: List<PlayerInfo>,
  private val mNewList: List<PlayerInfo>
) : DiffUtil.Callback() {

  override fun getOldListSize() = mOldList.size

  override fun getNewListSize() = mNewList.size

  override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
    // add a unique ID property on Contact and expose a getId() method
    return mOldList[oldItemPosition].id == mNewList[newItemPosition].id
  }

  override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
    val oldContent = mOldList[oldItemPosition]
    val newContent = mNewList[newItemPosition]

    return oldContent.firstName == newContent.firstName
  }

}

