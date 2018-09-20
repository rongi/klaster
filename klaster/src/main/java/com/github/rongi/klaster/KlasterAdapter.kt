package com.github.rongi.klaster

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import kotlinx.android.extensions.LayoutContainer

class KlasterAdapter<VH : RecyclerView.ViewHolder>(
  private val createViewHolder: (parent: ViewGroup, viewType: Int) -> VH,
  private val bindViewHolder: (viewHolder: VH, position: Int) -> Unit,
  private val getItemsCount: () -> Int
) : RecyclerView.Adapter<VH>() {

  override fun getItemCount() = getItemsCount()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
    createViewHolder.invoke(parent, viewType)

  override fun onBindViewHolder(holder: VH, position: Int) =
    bindViewHolder.invoke(holder, position)

}

class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer