package com.github.rongi.klaster

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class KlasterBuilder2(): KlasterBuilder<ViewHolder>() {

  private var layoutInflater: LayoutInflater? = null

  private var layoutBuilder: ((parent: ViewGroup, viewType: Int) -> View)? = null

  fun view(@LayoutRes viewResId: Int, initView: View.() -> Unit = {}): KlasterBuilder2 {
    layoutBuilder = { parent: ViewGroup, viewType: Int ->
      if (layoutInflater == null) throw KlasterException("LayoutInflater must be provided to use this method")
      layoutInflater!!.inflate(viewResId, parent, false).apply(initView)
    }

    return this
  }

  fun viewWithParent(createView: (parent: ViewGroup) -> View): KlasterBuilder2 {
    layoutBuilder = { parent: ViewGroup, viewType: Int ->
      createView(parent)
    }

    return this
  }

  fun view(createView: () -> View): KlasterBuilder2 {
    layoutBuilder = { _: ViewGroup, viewType: Int ->
      createView()
    }

    return this
  }

}
