package com.sample.app.presentation.extension

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.sample.app.R

class BindingUtils {
  companion object {
    private val TAG = BindingUtils::class.java.simpleName.toString()

    @JvmStatic
    @BindingAdapter("firstName", "lastName")
    fun setName(
      textView: TextView,
      firstName: String?,
      lastName: String?
    ) {
      textView.text = String.format(
        textView.context.getString(R.string.name),
        firstName?.replaceFirstChar(Char::uppercase),
        lastName?.replaceFirstChar(Char::uppercase)
      )
    }
  }

  }