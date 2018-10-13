package com.github.rongi.klaster

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * A builder to make [RecyclerView.Adapter] objects.
 */
class KlasterBuilder internal constructor() {

  private var viewBuilder: ((parent: ViewGroup, viewType: Int) -> View)? = null

  private var binder: ((viewHolder: KlasterViewHolder, position: Int) -> Unit)? = null

  private var binderWithPayloads: (KlasterViewHolder.(position: Int, payloads: MutableList<Any>) -> Unit)? = null

  private var getItemCount: (() -> Int)? = null

  private var getItemId: ((position: Int) -> Long)? = null

  private var getItemViewType: ((Int) -> Int)? = null

  private var setHasStableIds: (() -> Unit)? = null

  private var onAttachedToRecyclerView: ((recyclerView: RecyclerView) -> Unit)? = null

  private var onDetachedFromRecyclerView: ((recyclerView: RecyclerView) -> Unit)? = null

  private var onViewAttachedToWindow: ((holder: KlasterViewHolder) -> Unit)? = null

  private var onViewDetachedFromWindow: ((holder: KlasterViewHolder) -> Unit)? = null

  private var onFailedToRecycleView: ((holder: KlasterViewHolder) -> Boolean)? = null

  private var onViewRecycled: ((holder: KlasterViewHolder) -> Unit)? = null

  private var registerAdapterDataObserver: ((observer: RecyclerView.AdapterDataObserver) -> Unit)? = null

  private var unregisterAdapterDataObserver: ((observer: RecyclerView.AdapterDataObserver) -> Unit)? = null

  /**
   * Specify the layout id to be used to create new views.
   *
   * For adapters with single view type.
   *
   * @param initView a function to be called on the view after it was created. Default value is
   * and empty function.
   */
  fun view(@LayoutRes viewResId: Int, layoutInflater: LayoutInflater, initView: View.() -> Unit = {}): KlasterBuilder {
    viewBuilder = { parent: ViewGroup, _: Int ->
      layoutInflater.inflate(viewResId, parent, false).apply(initView)
    }

    return this
  }

  /**
   * Specify the function to be used to create views.
   *
   * For adapters with single view type.
   */
  fun viewBy(createView: () -> View): KlasterBuilder {
    viewBuilder = { _: ViewGroup, _: Int ->
      createView()
    }

    return this
  }

  /**
   * Specify the function to be used to create views. For the cases when you need the
   * parent parameter received in [RecyclerView.Adapter.onCreateViewHolder].
   *
   * For adapters with single view type.
   */
  fun view(createView: (parent: ViewGroup) -> View): KlasterBuilder {
    viewBuilder = { parent: ViewGroup, _: Int ->
      createView(parent)
    }

    return this
  }

  /**
   * Specify the function to be used to create views.
   *
   * For adapters with multiple view types.
   */
  fun view(createView: (viewType: Int, parent: ViewGroup) -> View): KlasterBuilder {
    viewBuilder = { parent: ViewGroup, viewType: Int -> createView(viewType, parent) }
    return this
  }

  /**
   * Specify a function to be used as [RecyclerView.Adapter.getItemCount].
   */
  fun itemCount(getItemsCount: (() -> Int)): KlasterBuilder {
    this.getItemCount = getItemsCount
    return this
  }

  /**
   * Specify a number to be used as a return values from [RecyclerView.Adapter.getItemCount].
   */
  fun itemCount(count: Int): KlasterBuilder {
    this.getItemCount = { count }
    return this
  }

  /**
   * Specify a function to be used as [RecyclerView.Adapter.onBindViewHolder].
   */
  fun bind(binder: KlasterViewHolder.(position: Int) -> Unit): KlasterBuilder {
    this.binder = binder
    return this
  }

  /**
   * Specify a function to be used as onBindViewHolder with payloads.
   */
  fun bind(binder: KlasterViewHolder.(position: Int, payloads: MutableList<Any>) -> Unit): KlasterBuilder {
    this.binderWithPayloads = binder
    return this
  }

  /**
   * Specify a function to be used as [RecyclerView.Adapter.getItemId].
   */
  fun getItemId(getItemId: (position: Int) -> Long): KlasterBuilder {
    this.getItemId = getItemId
    return this
  }

  /**
   * Specify a function to be used as [RecyclerView.Adapter.getItemViewType].
   */
  fun getItemViewType(getItemViewType: (Int) -> Int): KlasterBuilder {
    this.getItemViewType = getItemViewType
    return this
  }

  /**
   * Specify a function to be used as [RecyclerView.Adapter.setHasStableIds].
   */
  fun setHasStableIds(setHasStableIds: () -> Unit): KlasterBuilder {
    this.setHasStableIds = setHasStableIds
    return this
  }

  /**
   * Specify a function to be used as [RecyclerView.Adapter.onAttachedToRecyclerView].
   */
  fun onAttachedToRecyclerView(onAttachedToRecyclerView: (recyclerView: RecyclerView) -> Unit): KlasterBuilder {
    this.onAttachedToRecyclerView = onAttachedToRecyclerView
    return this
  }

  /**
   * Specify a function to be used as [RecyclerView.Adapter.onDetachedFromRecyclerView].
   */
  fun onDetachedFromRecyclerView(onDetachedFromRecyclerView: (recyclerView: RecyclerView) -> Unit): KlasterBuilder {
    this.onDetachedFromRecyclerView = onDetachedFromRecyclerView
    return this
  }

  /**
   * Specify a function to be used as [RecyclerView.Adapter.onViewAttachedToWindow].
   */
  fun onViewAttachedToWindow(onViewAttachedToWindow: (holder: KlasterViewHolder) -> Unit): KlasterBuilder {
    this.onViewAttachedToWindow = onViewAttachedToWindow
    return this
  }

  /**
   * Specify a function to be used as [RecyclerView.Adapter.onViewDetachedFromWindow].
   */
  fun onViewDetachedFromWindow(onViewDetachedFromWindow: (holder: KlasterViewHolder) -> Unit): KlasterBuilder {
    this.onViewDetachedFromWindow = onViewDetachedFromWindow
    return this
  }

  /**
   * Specify a function to be used as [RecyclerView.Adapter.onFailedToRecycleView].
   */
  fun onFailedToRecycleView(onFailedToRecycleView: (holder: KlasterViewHolder) -> Boolean): KlasterBuilder {
    this.onFailedToRecycleView = onFailedToRecycleView
    return this
  }

  /**
   * Specify a function to be used as [RecyclerView.Adapter.onViewRecycled].
   */
  fun onViewRecycled(onViewRecycled: (holder: KlasterViewHolder) -> Unit): KlasterBuilder {
    this.onViewRecycled = onViewRecycled
    return this
  }

  /**
   * Specify a function to be used as [RecyclerView.Adapter.registerAdapterDataObserver].
   */
  fun registerAdapterDataObserver(registerAdapterDataObserver: (observer: RecyclerView.AdapterDataObserver) -> Unit): KlasterBuilder {
    this.registerAdapterDataObserver = registerAdapterDataObserver
    return this
  }

  /**
   * Specify a function to be used as [RecyclerView.Adapter.unregisterAdapterDataObserver].
   */
  fun unregisterAdapterDataObserver(unregisterAdapterDataObserver: (observer: RecyclerView.AdapterDataObserver) -> Unit): KlasterBuilder {
    this.unregisterAdapterDataObserver = unregisterAdapterDataObserver
    return this
  }

  /**
   * Create the [RecyclerView.Adapter] instance.
   */
  fun build(): RecyclerView.Adapter<RecyclerView.ViewHolder> {
    if (getItemCount == null) throw StekkerException("Get items count function must be provided.")
    if (viewBuilder == null) throw StekkerException("View builder must be provided.")
    if (binder == null) throw StekkerException("'bind()' must be set.")

    @Suppress("UNCHECKED_CAST")
    return KlasterAdapter(
      _getItemCount = getItemCount!!,
      createViewHolder = { viewGroup, viewType ->
        KlasterViewHolder(viewBuilder!!.invoke(viewGroup, viewType))
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
