package com.sample.app.presentation.ui.home.memes

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sample.app.databinding.LayoutHomeItemBinding
import com.sample.app.domain.models.IPagingModel
import com.sample.app.presentation.ui.home.HomeFragment


/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class MemesAdapter : RecyclerView.Adapter<MemesAdapter.ViewHolder>() {

  private val values = mutableListOf<IPagingModel>()
  private var listener: ItemListener? = null

  fun updateItems(items: List<IPagingModel>) {
    // compute diffs
    val diffCallback = MemesDiffCallback(this.values, items)
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
      data: IPagingModel,
      listener: ItemListener?
    ) {

      itemRowBinding.apply {
        model = data
        //layout.layoutParams = ConstraintLayout.LayoutParams(data.width, data.height)
        root.setOnClickListener {
          listener?.onMemeClick(itemRowBinding.images, data)
        }
      }
    }
  }

  interface ItemListener {
    fun onMemeClick(view: ImageView, item: IPagingModel)
  }

}