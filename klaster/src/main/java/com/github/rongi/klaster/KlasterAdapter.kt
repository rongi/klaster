package com.github.rongi.klaster

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import kotlinx.android.extensions.LayoutContainer

class KlasterAdapter<ITEM>(
  private val createView: (parent: ViewGroup, viewType: Int) -> View,
  private val bindViewHolder: (viewHolder: ViewHolder, item: ITEM, position: Int) -> Unit
) : RecyclerView.Adapter<ViewHolder>() {

  var items: MutableList<ITEM> = mutableListOf()

  override fun getItemCount() = items.size

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
    ViewHolder(createView.invoke(parent, viewType))

  override fun onBindViewHolder(holder: ViewHolder, position: Int) =
    bindViewHolder.invoke(holder, items[position], position)

}

class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer