package com.github.rongi.stekker

import android.support.v7.widget.RecyclerView

object Stekker {

  fun <VH: RecyclerView.ViewHolder> withViewHolder(): StekkerWithViewHolderBuilder<VH> = StekkerWithViewHolderBuilder()

  fun get(): StekkerBuilder = StekkerBuilder()

}

class StekkerException(override val message: String) : RuntimeException()