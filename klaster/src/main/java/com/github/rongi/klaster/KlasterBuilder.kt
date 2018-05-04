package com.github.rongi.klaster

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class KlasterBuilder<ITEM> {

  private var layoutInflater: LayoutInflater? = null

  private var layoutBuilder: ((parent: ViewGroup, viewType: Int) -> View)? = null

  private var binder: ((viewHolder: ViewHolder, item: ITEM, position: Int) -> Unit)? = null

  fun view(@LayoutRes viewResId: Int): KlasterBuilder<ITEM> {
    layoutBuilder = { parent: ViewGroup, viewType: Int ->
      if (layoutInflater == null) throw KlasterException("LayoutInflater must be provided to use this method")
      layoutInflater!!.inflate(viewResId, parent, false)
    }

    return this
  }

  fun bind(binder: ViewHolder.(item: ITEM, position: Int) -> Unit): KlasterBuilder<ITEM> {
    this.binder = binder
    return this
  }

  fun bind(binder: ViewHolder.(item: ITEM) -> Unit): KlasterBuilder<ITEM> {
    this.binder = { viewHolder: ViewHolder, item: ITEM, position: Int ->
      binder(viewHolder, item)
    }
    return this
  }

  fun layoutInflater(layoutInflater: LayoutInflater): KlasterBuilder<ITEM> {
    this.layoutInflater = layoutInflater
    return this
  }

  fun build(): KlasterAdapter<ITEM> {
    if (layoutBuilder == null) throw KlasterException("Layout builder must be provided")
    if (binder == null) throw KlasterException("bind() must be set")

    return KlasterAdapter(
      createView = layoutBuilder!!,
      bindViewHolder = binder!!
    )
  }

}