package com.github.rongi.klaster

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.ViewGroup

/**
 * A builder to make [RecyclerView.Adapter] objects.
 *
 * It is intended for the cases when you need a custom [ViewHolder].
 */
class KlasterBuilderWithViewHolder<VH : RecyclerView.ViewHolder> internal constructor() {

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

  /**
   * Specify a function to be used as [RecyclerView.Adapter.onCreateViewHolder].
   */
  fun viewHolder(createViewHolder: (viewType: Int, parent: ViewGroup) -> VH): KlasterBuilderWithViewHolder<VH> {
    viewHolderBuilder = { parent: ViewGroup, viewType: Int ->
      createViewHolder(viewType, parent)
    }

    return this
  }

  /**
   * Specify a function to be used as [RecyclerView.Adapter.getItemCount].
   */
  fun itemCount(getItemsCount: (() -> Int)): KlasterBuilderWithViewHolder<VH> {
    this.getItemCount = getItemsCount
    return this
  }

  /**
   * Specify a number to be used as a return values from [RecyclerView.Adapter.getItemCount].
   */
  fun itemCount(count: Int): KlasterBuilderWithViewHolder<VH> {
    this.getItemCount = { count }
    return this
  }

  /**
   * Specify a function to be used as [RecyclerView.Adapter.onBindViewHolder].
   */
  fun bind(binder: VH.(position: Int) -> Unit): KlasterBuilderWithViewHolder<VH> {
    this.binder = binder
    return this
  }

  /**
   * Specify a function to be used as onBindViewHolder with payloads.
   */
  fun bind(binder: VH.(position: Int, payloads: MutableList<Any>) -> Unit): KlasterBuilderWithViewHolder<VH> {
    this.binderWithPayloads = binder
    return this
  }

  /**
   * Specify a function to be used as [RecyclerView.Adapter.getItemId].
   */
  fun getItemId(getItemId: (position: Int) -> Long): KlasterBuilderWithViewHolder<VH> {
    this.getItemId = getItemId
    return this
  }

  /**
   * Specify a function to be used as [RecyclerView.Adapter.getItemViewType].
   */
  fun getItemViewType(getItemViewType: (Int) -> Int): KlasterBuilderWithViewHolder<VH> {
    this.getItemViewType = getItemViewType
    return this
  }

  /**
   * Specify a function to be used as [RecyclerView.Adapter.setHasStableIds].
   */
  fun setHasStableIds(setHasStableIds: () -> Unit): KlasterBuilderWithViewHolder<VH> {
    this.setHasStableIds = setHasStableIds
    return this
  }

  /**
   * Specify a function to be used as [RecyclerView.Adapter.onAttachedToRecyclerView].
   */
  fun onAttachedToRecyclerView(onAttachedToRecyclerView: (recyclerView: RecyclerView) -> Unit): KlasterBuilderWithViewHolder<VH> {
    this.onAttachedToRecyclerView = onAttachedToRecyclerView
    return this
  }

  /**
   * Specify a function to be used as [RecyclerView.Adapter.onDetachedFromRecyclerView].
   */
  fun onDetachedFromRecyclerView(onDetachedFromRecyclerView: (recyclerView: RecyclerView) -> Unit): KlasterBuilderWithViewHolder<VH> {
    this.onDetachedFromRecyclerView = onDetachedFromRecyclerView
    return this
  }

  /**
   * Specify a function to be used as [RecyclerView.Adapter.onViewAttachedToWindow].
   */
  fun onViewAttachedToWindow(onViewAttachedToWindow: (holder: VH) -> Unit): KlasterBuilderWithViewHolder<VH> {
    this.onViewAttachedToWindow = onViewAttachedToWindow
    return this
  }

  /**
   * Specify a function to be used as [RecyclerView.Adapter.onViewDetachedFromWindow].
   */
  fun onViewDetachedFromWindow(onViewDetachedFromWindow: (holder: VH) -> Unit): KlasterBuilderWithViewHolder<VH> {
    this.onViewDetachedFromWindow = onViewDetachedFromWindow
    return this
  }

  /**
   * Specify a function to be used as [RecyclerView.Adapter.onFailedToRecycleView].
   */
  fun onFailedToRecycleView(onFailedToRecycleView: (holder: VH) -> Boolean): KlasterBuilderWithViewHolder<VH> {
    this.onFailedToRecycleView = onFailedToRecycleView
    return this
  }

  /**
   * Specify a function to be used as [RecyclerView.Adapter.onViewRecycled].
   */
  fun onViewRecycled(onViewRecycled: (holder: VH) -> Unit): KlasterBuilderWithViewHolder<VH> {
    this.onViewRecycled = onViewRecycled
    return this
  }

  /**
   * Specify a function to be used as [RecyclerView.Adapter.registerAdapterDataObserver].
   */
  fun registerAdapterDataObserver(registerAdapterDataObserver: (observer: RecyclerView.AdapterDataObserver) -> Unit): KlasterBuilderWithViewHolder<VH> {
    this.registerAdapterDataObserver = registerAdapterDataObserver
    return this
  }

  /**
   * Specify a function to be used as [RecyclerView.Adapter.unregisterAdapterDataObserver].
   */
  fun unregisterAdapterDataObserver(unregisterAdapterDataObserver: (observer: RecyclerView.AdapterDataObserver) -> Unit): KlasterBuilderWithViewHolder<VH> {
    this.unregisterAdapterDataObserver = unregisterAdapterDataObserver
    return this
  }

  /**
   * Create the [RecyclerView.Adapter] instance.
   */
  fun build(): RecyclerView.Adapter<RecyclerView.ViewHolder> {
    if (getItemCount == null) throw StekkerException("Get items count function must be provided.")
    if (viewHolderBuilder == null) throw StekkerException("View holder builder must be provided.")
    if (binder == null) throw StekkerException("bind() must be set.")

    @Suppress("UNCHECKED_CAST")
    return KlasterAdapter(
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
