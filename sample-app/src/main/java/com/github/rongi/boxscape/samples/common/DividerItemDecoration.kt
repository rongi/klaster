package com.github.rongi.boxscape.samples.common

import android.content.res.Resources
import android.graphics.Canvas
import android.support.v7.widget.RecyclerView
import com.github.rongi.boxscape.samples.R
import java.lang.Math.round

private const val LEFT_PADDING = 12f
private const val RIGHT_PADDING = 12f

/**
 * Divider for RecyclerView
 */
class DividerItemDecoration(resources: Resources) : RecyclerView.ItemDecoration() {

  private val leftPaddingPx = toPixels(LEFT_PADDING, resources)
  private val rightPaddingPx = toPixels(RIGHT_PADDING, resources)
  private val divider = resources.getDrawable(R.drawable.divider)

  override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State?) {
    val left = parent.paddingLeft + leftPaddingPx
    val right = parent.width - parent.paddingRight - rightPaddingPx

    val childCount = parent.childCount
    for (i in 0 until childCount) {
      val child = parent.getChildAt(i)

      val params = child.layoutParams as RecyclerView.LayoutParams

      val top = child.bottom + params.bottomMargin
      val bottom = top + divider.intrinsicHeight

      divider.setBounds(left, top, right, bottom)
      divider.draw(c)
    }
  }

}

private fun toPixels(dipsValue: Float, resources: Resources?): Int {
  return if (resources == null) {
    (dipsValue * 2f).toInt()
  } else {
    round(dipsValue * resources.displayMetrics.density) // For layout visual editor
  }
}
