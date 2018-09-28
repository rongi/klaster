package com.github.rongi.stekker

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

class StekkerWithViewHolderBuilder<VH : RecyclerView.ViewHolder> {

  private var viewHolderBuilder: ((parent: ViewGroup, viewType: Int) -> VH)? = null

  private var binder: ((viewHolder: VH, position: Int) -> Unit)? = null

  private var binderWithPayloads: (VH.(position: Int, payloads: MutableList<Any>) -> Unit)? = null

  private var getItemCount: (() -> Int)? = null

  private var getItemId: ((position: Int) -> Long)? = null

  private var getItemViewType: ((Int) -> Int)? = null

  private var setHasStableIds: (() -> Unit)? = null

  private var onAttachedToRecyclerView: ((recyclerView: RecyclerView) -> Unit)? = null

  private var onDetachedFromRecyclerView: ((recyclerView: RecyclerView) -> Unit)? = null

  private var onViewAttachedToWindow: ((holder: VH) -> Unit)? = null

  private var onViewDetachedFromWindow: ((holder: VH) -> Unit)? = null

  private var onFailedToRecycleView: ((holder: VH) -> Boolean)? = null

  private var onViewRecycled: ((holder: VH) -> Unit)? = null

  private var registerAdapterDataObserver: ((observer: RecyclerView.AdapterDataObserver) -> Unit)? = null

  private var unregisterAdapterDataObserver: ((observer: RecyclerView.AdapterDataObserver) -> Unit)? = null

  fun viewHolder(createViewHolder: (parent: ViewGroup) -> VH): StekkerWithViewHolderBuilder<VH> {
    viewHolderBuilder = { parent: ViewGroup, _: Int ->
      createViewHolder(parent)
    }

    return this
  }

  fun itemCount(getItemsCount: (() -> Int)): StekkerWithViewHolderBuilder<VH> {
    this.getItemCount = getItemsCount
    return this
  }

  fun itemCount(count: Int): StekkerWithViewHolderBuilder<VH> {
    this.getItemCount = { count }
    return this
  }

  fun bind(binder: VH.(position: Int) -> Unit): StekkerWithViewHolderBuilder<VH> {
    this.binder = binder
    return this
  }

  fun bind(binder: VH.(position: Int, payloads: MutableList<Any>) -> Unit): StekkerWithViewHolderBuilder<VH> {
    this.binderWithPayloads = binder
    return this
  }

  fun getItemId(getItemId: (position: Int) -> Long): StekkerWithViewHolderBuilder<VH> {
    this.getItemId = getItemId
    return this
  }

  fun getItemViewType(getItemViewType: (Int) -> Int): StekkerWithViewHolderBuilder<VH> {
    this.getItemViewType = getItemViewType
    return this
  }

  fun setHasStableIds(setHasStableIds: () -> Unit): StekkerWithViewHolderBuilder<VH> {
    this.setHasStableIds = setHasStableIds
    return this
  }

  fun onAttachedToRecyclerView(onAttachedToRecyclerView: (recyclerView: RecyclerView) -> Unit): StekkerWithViewHolderBuilder<VH> {
    this.onAttachedToRecyclerView = onAttachedToRecyclerView
    return this
  }

  fun onDetachedFromRecyclerView(onDetachedFromRecyclerView: (recyclerView: RecyclerView) -> Unit): StekkerWithViewHolderBuilder<VH> {
    this.onDetachedFromRecyclerView = onDetachedFromRecyclerView
    return this
  }

  fun onViewAttachedToWindow(onViewAttachedToWindow: (holder: VH) -> Unit): StekkerWithViewHolderBuilder<VH> {
    this.onViewAttachedToWindow = onViewAttachedToWindow
    return this
  }

  fun onViewDetachedFromWindow(onViewDetachedFromWindow: (holder: VH) -> Unit): StekkerWithViewHolderBuilder<VH> {
    this.onViewDetachedFromWindow = onViewDetachedFromWindow
    return this
  }

  fun onFailedToRecycleView(onFailedToRecycleView: (holder: VH) -> Boolean): StekkerWithViewHolderBuilder<VH> {
    this.onFailedToRecycleView = onFailedToRecycleView
    return this
  }

  fun onViewRecycled(onViewRecycled: (holder: VH) -> Unit): StekkerWithViewHolderBuilder<VH> {
    this.onViewRecycled = onViewRecycled
    return this
  }

  fun registerAdapterDataObserver(registerAdapterDataObserver: (observer: RecyclerView.AdapterDataObserver) -> Unit): StekkerWithViewHolderBuilder<VH> {
    this.registerAdapterDataObserver = registerAdapterDataObserver
    return this
  }

  fun unregisterAdapterDataObserver(unregisterAdapterDataObserver: (observer: RecyclerView.AdapterDataObserver) -> Unit): StekkerWithViewHolderBuilder<VH> {
    this.unregisterAdapterDataObserver = unregisterAdapterDataObserver
    return this
  }

  fun build(): RecyclerView.Adapter<RecyclerView.ViewHolder> {
    if (getItemCount == null) throw StekkerException("Items count function must be provided")
    if (viewHolderBuilder == null) throw StekkerException("View holder builder must be provided")
    if (binder == null) throw StekkerException("bind() must be set")

    @Suppress("UNCHECKED_CAST")
    return StekkerAdapter(
      _getItemCount = getItemCount!!,
      createViewHolder = viewHolderBuilder!!,
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
