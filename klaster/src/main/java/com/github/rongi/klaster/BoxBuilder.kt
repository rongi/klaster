package com.github.rongi.klaster

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class BoxBuilder<ITEM> {

  private var layoutInflater: LayoutInflater? = null

  private var layoutBuilder: ((parent: ViewGroup, viewType: Int) -> View)? = null

  private var binder: ((viewHolder: ViewHolder, item: ITEM, position: Int) -> Unit)? = null

  fun view(@LayoutRes viewResId: Int): BoxBuilder<ITEM> {
    layoutBuilder = { parent: ViewGroup, viewType: Int ->
      if (layoutInflater == null) throw BoxException("LayoutInflater must be provided to use this method")
      layoutInflater!!.inflate(viewResId, parent, false)
    }

    return this
  }

  fun bind(binder: ViewHolder.(item: ITEM, position: Int) -> Unit): BoxBuilder<ITEM> {
    this.binder = binder
    return this
  }

  fun bind(binder: ViewHolder.(item: ITEM) -> Unit): BoxBuilder<ITEM> {
    this.binder = { viewHolder: ViewHolder, item: ITEM, position: Int ->
      binder(viewHolder, item)
    }
    return this
  }

  fun layoutInflater(layoutInflater: LayoutInflater): BoxBuilder<ITEM> {
    this.layoutInflater = layoutInflater
    return this
  }

  fun build(): BoxAdapter<ITEM> {
    if (layoutBuilder == null) throw BoxException("Layout builder must be provided")
    if (binder == null) throw BoxException("bind() must be set")

    return BoxAdapter(
      createView = layoutBuilder!!,
      bindViewHolder = binder!!
    )
  }

}