package com.github.rongi.klaster

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class KlasterBuilder {

  private var layoutInflater: LayoutInflater? = null

  private var layoutBuilder: ((parent: ViewGroup, viewType: Int) -> View)? = null

  private var binder: ((viewHolder: ViewHolder, position: Int) -> Unit)? = null

  private var getItemsCount: (() -> Int)? = null

  fun view(@LayoutRes viewResId: Int, initView: View.() -> Unit = {}): KlasterBuilder {
    layoutBuilder = { parent: ViewGroup, viewType: Int ->
      if (layoutInflater == null) throw KlasterException("LayoutInflater must be provided to use this method")
      layoutInflater!!.inflate(viewResId, parent, false).apply(initView)
    }

    return this
  }

  fun viewWithParent(createView: (parent: ViewGroup) -> View): KlasterBuilder {
    layoutBuilder = { parent: ViewGroup, viewType: Int ->
      createView(parent)
    }

    return this
  }

  fun view(createView: () -> View): KlasterBuilder {
    layoutBuilder = { _: ViewGroup, viewType: Int ->
      createView()
    }

    return this
  }
  
  fun getItemsCount(getItemsCount: (() -> Int)): KlasterBuilder {
    this.getItemsCount = getItemsCount
    return this
  }

  fun bind(binder: ViewHolder.(position: Int) -> Unit): KlasterBuilder {
    this.binder = binder
    return this
  }

  fun useLayoutInflater(layoutInflater: LayoutInflater): KlasterBuilder {
    this.layoutInflater = layoutInflater
    return this
  }

  fun build(): KlasterAdapter {
    if (layoutBuilder == null) throw KlasterException("Layout builder must be provided")
    if (binder == null) throw KlasterException("bind() must be set")

    return KlasterAdapter(
      getItemsCount = getItemsCount!!,
      createView = layoutBuilder!!,
      bindViewHolder = binder!!
    )
  }

}