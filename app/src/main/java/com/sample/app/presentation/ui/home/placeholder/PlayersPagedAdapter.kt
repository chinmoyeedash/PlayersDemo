package com.sample.app.presentation.ui.home.placeholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sample.app.databinding.LayoutHomeItemBinding
import com.sample.app.presentation.model.PlayerInfo
import com.sample.app.presentation.ui.home.HomeFragment
import com.sample.app.presentation.ui.home.placeholder.PlaceholderContent.PlaceholderItem


/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class PlayersPagedAdapter : PagingDataAdapter<PlayerInfo, PlayersPagedAdapter.ViewHolder>(PlayersDiff) {

  private var listener: PlayerInfoListener? = null

  /*fun updateItems(items: List<PlayerInfo>) {
    // compute diffs
    val diffCallback = PlayersDiffCallback(this.values, items)
    val diffResult = DiffUtil.calculateDiff(diffCallback)

    // clear contacts and add
    this.values.clear()
    this.values.addAll(items)

    diffResult.dispatchUpdatesTo(this) // calls adapter's notify methods after diff is computed
  }*/

  fun setListener(listener: HomeFragment) {
    this.listener = listener
  }

  object PlayersDiff : DiffUtil.ItemCallback<PlayerInfo>() {
    override fun areItemsTheSame(oldItem: PlayerInfo, newItem: PlayerInfo): Boolean {
      // Id is unique.
      return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PlayerInfo, newItem: PlayerInfo): Boolean {
      return oldItem == newItem
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

    return ViewHolder(
      LayoutHomeItemBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
      )
    )

  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bind(getItem(position)!!, listener)

  }


  inner class ViewHolder(binding: LayoutHomeItemBinding) : RecyclerView.ViewHolder(binding.root) {
    private var itemRowBinding: LayoutHomeItemBinding = binding
    fun bind(
      data: PlayerInfo,
      listener: PlayerInfoListener?
    ) {

      itemRowBinding.apply {
        model = data
        root.setOnClickListener {
          listener?.onPlayerInfoClick(data)
        }
      }
    }
  }

  interface PlayerInfoListener {
    fun onPlayerInfoClick(item: PlayerInfo)
  }

}