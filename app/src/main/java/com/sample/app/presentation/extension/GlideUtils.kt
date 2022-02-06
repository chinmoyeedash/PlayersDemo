package com.sample.app.presentation.extension

import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

class GlideUtils {

  companion object {

    @JvmStatic
    @BindingAdapter(value = ["imageUrl", "errorRes", "placeHolderRes"], requireAll = false)
    fun load(
      view: ImageView,
      imageUrl: String?,
      errorRes: Drawable? = null,
      placeHolderRes: Drawable? = null
    ) {
      val glide = Glide.with(view.context)
      when (!TextUtils.isEmpty(imageUrl)) {
        true -> {
          val requestOptions = RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .priority(Priority.IMMEDIATE)
          if (placeHolderRes != null) {
            glide
              .load(imageUrl)
              .placeholder(placeHolderRes)
              .apply(requestOptions)
              .error(errorRes)
              .into(view)

          } else {
            glide
              .load(imageUrl)
              .apply(requestOptions)
              .error(errorRes)
              .into(view)
          }
        }
      }
    }
  }
}
