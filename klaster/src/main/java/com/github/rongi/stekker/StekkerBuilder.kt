package com.github.rongi.stekker

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class StekkerBuilder {

  private var viewBuilder: ((parent: ViewGroup, viewType: Int) -> View)? = null

  private var binder: ((viewHolder: SimpleViewHolder, position: Int) -> Unit)? = null

  private var binderWithPayloads: (SimpleViewHolder.(position: Int, payloads: MutableList<Any>) -> Unit)? = null

  private var getItemCount: (() -> Int)? = null

  private var getItemId: ((position: Int) -> Long)? = null

  private var getItemViewType: ((Int) -> Int)? = null

  private var setHasStableIds: (() -> Unit)? = null

  private var onAttachedToRecyclerView: ((recyclerView: RecyclerView) -> Unit)? = null

  private var onDetachedFromRecyclerView: ((recyclerView: RecyclerView) -> Unit)? = null

  private var onViewAttachedToWindow: ((holder: SimpleViewHolder) -> Unit)? = null

  private var onViewDetachedFromWindow: ((holder: SimpleViewHolder) -> Unit)? = null

  private var onFailedToRecycleView: ((holder: SimpleViewHolder) -> Boolean)? = null

  private var onViewRecycled: ((holder: SimpleViewHolder) -> Unit)? = null

  private var registerAdapterDataObserver: ((observer: RecyclerView.AdapterDataObserver) -> Unit)? = null

  private var unregisterAdapterDataObserver: ((observer: RecyclerView.AdapterDataObserver) -> Unit)? = null

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

  fun bind(binder: SimpleViewHolder.(position: Int, payloads: MutableList<Any>) -> Unit): StekkerBuilder {
    this.binderWithPayloads = binder
    return this
  }

  fun getItemId(getItemId: (position: Int) -> Long): StekkerBuilder {
    this.getItemId = getItemId
    return this
  }

  fun getItemViewType(getItemViewType: (Int) -> Int): StekkerBuilder {
    this.getItemViewType = getItemViewType
    return this
  }

  fun setHasStableIds(setHasStableIds: () -> Unit): StekkerBuilder {
    this.setHasStableIds = setHasStableIds
    return this
  }

  fun onAttachedToRecyclerView(onAttachedToRecyclerView: (recyclerView: RecyclerView) -> Unit): StekkerBuilder {
    this.onAttachedToRecyclerView = onAttachedToRecyclerView
    return this
  }

  fun onDetachedFromRecyclerView(onDetachedFromRecyclerView: (recyclerView: RecyclerView) -> Unit): StekkerBuilder {
    this.onDetachedFromRecyclerView = onDetachedFromRecyclerView
    return this
  }

  fun onViewAttachedToWindow(onViewAttachedToWindow: (holder: SimpleViewHolder) -> Unit): StekkerBuilder {
    this.onViewAttachedToWindow = onViewAttachedToWindow
    return this
  }

  fun onViewDetachedFromWindow(onViewDetachedFromWindow: (holder: SimpleViewHolder) -> Unit): StekkerBuilder {
    this.onViewDetachedFromWindow = onViewDetachedFromWindow
    return this
  }

  fun onFailedToRecycleView(onFailedToRecycleView: (holder: SimpleViewHolder) -> Boolean): StekkerBuilder {
    this.onFailedToRecycleView = onFailedToRecycleView
    return this
  }

  fun onViewRecycled(onViewRecycled: (holder: SimpleViewHolder) -> Unit): StekkerBuilder {
    this.onViewRecycled = onViewRecycled
    return this
  }

  fun registerAdapterDataObserver(registerAdapterDataObserver: (observer: RecyclerView.AdapterDataObserver) -> Unit): StekkerBuilder {
    this.registerAdapterDataObserver = registerAdapterDataObserver
    return this
  }

  fun unregisterAdapterDataObserver(unregisterAdapterDataObserver: (observer: RecyclerView.AdapterDataObserver) -> Unit): StekkerBuilder {
    this.unregisterAdapterDataObserver = unregisterAdapterDataObserver
    return this
  }

  fun build(): RecyclerView.Adapter<RecyclerView.ViewHolder> {
    if (getItemCount == null) throw StekkerException("Items count function must be provided")
    if (viewBuilder == null) throw StekkerException("View builder must be provided")
    if (binder == null) throw StekkerException("'bind()' must be set")

    @Suppress("UNCHECKED_CAST")
    return StekkerAdapter(
      _getItemCount = getItemCount!!,
      createViewHolder = { viewGroup, viewType ->
        SimpleViewHolder(viewBuilder!!.invoke(viewGroup, viewType))
      },
      bindViewHolder = binder!!,
      bindViewHolderWithPayloads = binderWithPayloads,
      _getItemId = getItemId,
      _getItemViewType = getItemViewType,
      _setHasStableIds = setHasStableIds,
      _onAttachedToRecyclerView = onAttachedToRecyclerView,
      _onDetachedFromRecyclerView = onDetachedFromRecyclerView,
      _onViewAttachedToWindow = onViewAttachedToWindow,
      _onViewDetachedFromWindow = onViewDetachedFromWindow,
      _onFailedToRecycleView = onFailedToRecycleView,
      _onViewRecycled = onViewRecycled,
      _registerAdapterDataObserver = registerAdapterDataObserver,
      _unregisterAdapterDataObserver = unregisterAdapterDataObserver
    ) as RecyclerView.Adapter<RecyclerView.ViewHolder>
  }

}
