package com.github.rongi.klaster

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

open class KlasterBuilder<VH: RecyclerView.ViewHolder> {

  private var viewHolderBuilder: ((parent: ViewGroup, viewType: Int) -> VH)? = null

  private var binder: ((viewHolder: VH, position: Int) -> Unit)? = null

  private var getItemsCount: (() -> Int)? = null

  fun viewHolder(createViewHolder: (parent: ViewGroup) -> VH): KlasterBuilder<VH> {
    viewHolderBuilder = { parent: ViewGroup, _: Int ->
      createViewHolder(parent)
    }

    return this
  }

  fun viewHolder(createViewHolder: () -> VH): KlasterBuilder<VH> {
    viewHolderBuilder = { _: ViewGroup, _: Int ->
      createViewHolder()
    }

    return this
  }

  fun getItemsCount(getItemsCount: (() -> Int)): KlasterBuilder<VH> {
    this.getItemsCount = getItemsCount
    return this
  }

  fun bind(binder: VH.(position: Int) -> Unit): KlasterBuilder<VH> {
    this.binder = binder
    return this
  }

  fun build(): KlasterAdapter<VH> {
    if (getItemsCount == null) throw KlasterException("Items count function must be provided")
    if (viewHolderBuilder == null) throw KlasterException("View holder builder must be provided")
    if (binder == null) throw KlasterException("bind() must be set")

    return KlasterAdapter(
      getItemsCount = getItemsCount!!,
      createViewHolder = viewHolderBuilder!!,
      bindViewHolder = binder!!
    )
  }

}
