package com.github.rongi.stekker

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import kotlinx.android.extensions.LayoutContainer

class StekkerAdapter<VH : RecyclerView.ViewHolder>(
  private val createViewHolder: (parent: ViewGroup, viewType: Int) -> VH,
  private val bindViewHolder: (viewHolder: VH, position: Int) -> Unit,
  private val bindViewHolderWithPayloads: ((viewHolder: VH, position: Int, payloads: MutableList<Any>) -> Unit)?,
  private val _getItemCount: () -> Int,
  private val _getItemId: ((position: Int) -> Long)?,
  private val _getItemViewType: ((Int) -> Int)?,
  private val _setHasStableIds: (() -> Unit)?,
  private val _onAttachedToRecyclerView: ((recyclerView: RecyclerView) -> Unit)?,
  private val _onDetachedFromRecyclerView: ((recyclerView: RecyclerView) -> Unit)?,
  private val _onViewAttachedToWindow: ((holder: VH) -> Unit)?,
  private val _onViewDetachedFromWindow: ((holder: VH) -> Unit)?,
  private val _onFailedToRecycleView: ((holder: VH) -> Boolean)?,
  private val _onViewRecycled: ((holder: VH) -> Unit)?,
  private val _registerAdapterDataObserver: ((observer: RecyclerView.AdapterDataObserver) -> Unit)?,
  private val _unregisterAdapterDataObserver: ((observer: RecyclerView.AdapterDataObserver) -> Unit)?
) : RecyclerView.Adapter<VH>() {

  override fun getItemCount() = _getItemCount()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
    createViewHolder.invoke(parent, viewType)

  override fun onBindViewHolder(holder: VH, position: Int) =
    bindViewHolder.invoke(holder, position)

  override fun onBindViewHolder(holder: VH, position: Int, payloads: MutableList<Any>) =
    bindViewHolderWithPayloads?.invoke(holder, position, payloads) ?: super.onBindViewHolder(holder, position, payloads)

  override fun getItemId(position: Int): Long =
    _getItemId?.invoke(position) ?: super.getItemId(position)

  override fun getItemViewType(position: Int): Int =
    _getItemViewType?.invoke(position) ?: super.getItemViewType(position)

  override fun setHasStableIds(hasStableIds: Boolean): Unit =
    _setHasStableIds?.invoke() ?: super.setHasStableIds(hasStableIds)

  override fun onAttachedToRecyclerView(recyclerView: RecyclerView) =
    _onAttachedToRecyclerView?.invoke(recyclerView) ?: super.onAttachedToRecyclerView(recyclerView)

  override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) =
    _onDetachedFromRecyclerView?.invoke(recyclerView) ?: super.onDetachedFromRecyclerView(recyclerView)

  override fun onViewAttachedToWindow(holder: VH) =
    _onViewAttachedToWindow?.invoke(holder) ?: super.onViewAttachedToWindow(holder)

  override fun onViewDetachedFromWindow(holder: VH) =
    _onViewDetachedFromWindow?.invoke(holder) ?: super.onViewDetachedFromWindow(holder)

  override fun onFailedToRecycleView(holder: VH): Boolean =
    _onFailedToRecycleView?.invoke(holder) ?: super.onFailedToRecycleView(holder)

  override fun onViewRecycled(holder: VH) =
    _onViewRecycled?.invoke(holder) ?: super.onViewRecycled(holder)

  override fun registerAdapterDataObserver(observer: RecyclerView.AdapterDataObserver) =
    _registerAdapterDataObserver?.invoke(observer) ?: super.registerAdapterDataObserver(observer)

  override fun unregisterAdapterDataObserver(observer: RecyclerView.AdapterDataObserver) =
    _unregisterAdapterDataObserver?.invoke(observer) ?: super.unregisterAdapterDataObserver(observer)

}

class SimpleViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer
