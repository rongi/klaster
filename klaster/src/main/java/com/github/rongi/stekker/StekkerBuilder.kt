package com.github.rongi.stekker

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class StekkerBuilder {

  private var binder: ((viewHolder: SimpleViewHolder, position: Int) -> Unit)? = null

  private var viewBuilder: ((parent: ViewGroup, viewType: Int) -> View)? = null

  private var getItemCount: (() -> Int)? = null

  private var getItemId: ((position: Int) -> Long)? = null

  fun view(@LayoutRes viewResId: Int, layoutInflater: LayoutInflater, initView: View.() -> Unit = {}): StekkerBuilder {
    viewBuilder = { parent: ViewGroup, _: Int ->
      layoutInflater.inflate(viewResId, parent, false).apply(initView)
    }

    return this
  }

  fun view(createView: () -> View): StekkerBuilder {
    viewBuilder = { _: ViewGroup, _: Int ->
      createView()
    }

    return this
  }

  fun viewWithParent(createView: (parent: ViewGroup) -> View): StekkerBuilder {
    viewBuilder = { parent: ViewGroup, _: Int ->
      createView(parent)
    }

    return this
  }

  fun itemCount(getItemsCount: (() -> Int)): StekkerBuilder {
    this.getItemCount = getItemsCount
    return this
  }

  fun itemCount(count: Int): StekkerBuilder {
    this.getItemCount = { count }
    return this
  }

  fun bind(binder: SimpleViewHolder.(position: Int) -> Unit): StekkerBuilder {
    this.binder = binder
    return this
  }

  fun getItemId(getItemId: (position: Int) -> Long): StekkerBuilder {
    this.getItemId = getItemId
    return this
  }

  fun build(): RecyclerView.Adapter<SimpleViewHolder> {
    if (getItemCount == null) throw StekkerException("Items count function must be provided")
    if (viewBuilder == null) throw StekkerException("View builder must be provided")
    if (binder == null) throw StekkerException("'bind()' must be set")

    return StekkerAdapter(
      _getItemCount = getItemCount!!,
      createViewHolder = { viewGroup, position ->
        SimpleViewHolder(viewBuilder!!.invoke(viewGroup, position))
      },
      bindViewHolder = binder!!,
      _getItemId = getItemId
    )
  }

}
