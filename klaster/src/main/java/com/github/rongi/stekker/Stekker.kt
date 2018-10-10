package com.github.rongi.stekker

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder

object Stekker {

  /**
   * Returns new [StekkerBuilder].
   */
  fun get(): StekkerBuilder = StekkerBuilder()

  /**
   * Returns new [StekkerBuilderWithViewHolder].
   *
   * It is intended for the cases when you need a custom [ViewHolder].
   */
  fun <VH : RecyclerView.ViewHolder> withViewHolder(): StekkerBuilderWithViewHolder<VH> = StekkerBuilderWithViewHolder()

}

class StekkerException(override val message: String) : RuntimeException()
