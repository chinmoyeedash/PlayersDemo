package com.sample.app.presentation.utils

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.core.view.ViewCompat
import androidx.core.view.ViewCompat.NestedScrollType
import androidx.annotation.NonNull


class BottomAnimBehavior(context: Context, attrs: AttributeSet) :
  CoordinatorLayout.Behavior<BottomNavigationView?>(context, attrs) {
  override fun onStartNestedScroll(
    coordinatorLayout: CoordinatorLayout,
    child: BottomNavigationView,
    directTargetChild: View,
    target: View,
    axes: Int,
    type: Int
  ): Boolean {
    return axes == ViewCompat.SCROLL_AXIS_VERTICAL
  }

  override fun onNestedPreScroll(
    coordinatorLayout: CoordinatorLayout,
    child: BottomNavigationView,
    target: View,
    dx: Int,
    dy: Int,
    consumed: IntArray,
    type: Int
  ) {
    super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
    child.translationY = Math.max(0.0f, Math.min(child.height.toFloat(), child.translationY + dy))
  }
}