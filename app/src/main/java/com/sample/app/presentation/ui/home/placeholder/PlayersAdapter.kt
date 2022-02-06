package com.sample.app.presentation.ui.home.placeholder

import android.view.LayoutInflater
import android.view.ViewGroup
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
class PlayersAdapter : RecyclerView.Adapter<PlayersAdapter.ViewHolder>() {

  private val values = mutableListOf<PlayerInfo>()
  private var listener: PlayerInfoListener? = null

  fun updateItems(items: List<PlayerInfo>) {
    // compute diffs
    val diffCallback = PlayersDiffCallback(this.values, items)
    val diffResult = DiffUtil.calculateDiff(diffCallback)

    // clear contacts and add
    this.values.clear()
    this.values.addAll(items)

    diffResult.dispatchUpdatesTo(this) // calls adapter's notify methods after diff is computed
  }

  fun setListener(listener: HomeFragment) {
    this.listener = listener
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
    holder.bind(values[position], listener)

  }

  override fun getItemCount(): Int = values.size


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