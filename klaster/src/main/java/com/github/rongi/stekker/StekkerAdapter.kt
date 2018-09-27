package com.github.rongi.stekker

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import kotlinx.android.extensions.LayoutContainer

class StekkerAdapter<VH : RecyclerView.ViewHolder>(
  private val createViewHolder: (parent: ViewGroup, viewType: Int) -> VH,
  private val bindViewHolder: (viewHolder: VH, position: Int) -> Unit,
  private val _getItemCount: () -> Int
) : RecyclerView.Adapter<VH>() {

  override fun getItemCount() = _getItemCount()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
    createViewHolder.invoke(parent, viewType)

  override fun onBindViewHolder(holder: VH, position: Int) =
    bindViewHolder.invoke(holder, position)

}

class SimpleViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer

typealias SimpleAdapter = RecyclerView.Adapter<SimpleViewHolder>