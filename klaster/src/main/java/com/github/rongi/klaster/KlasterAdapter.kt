package com.github.rongi.klaster

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import kotlinx.android.extensions.LayoutContainer

class KlasterAdapter(
  private val createView: (parent: ViewGroup, viewType: Int) -> View,
  private val bindViewHolder: (viewHolder: ViewHolder, position: Int) -> Unit,
  private val getItemsCount: () -> Int
) : RecyclerView.Adapter<ViewHolder>() {

  override fun getItemCount() = getItemsCount()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
    ViewHolder(createView.invoke(parent, viewType))

  override fun onBindViewHolder(holder: ViewHolder, position: Int) =
    bindViewHolder.invoke(holder, position)

}

class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer