package com.github.rongi.klaster

import android.support.v7.widget.RecyclerView

object Klaster {

  fun <VH: RecyclerView.ViewHolder> builderWithViewHolder(): KlasterBuilder<VH> = KlasterBuilder()

  fun builder(): KlasterBuilder<ViewHolder> = KlasterBuilder()

}

class KlasterException(override val message: String) : RuntimeException()