package com.github.rongi.klaster

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import kotlinx.android.extensions.LayoutContainer
import java.util.Collections.emptyList

class BoxAdapter<ITEM>(
  private val createView: (parent: ViewGroup, viewType: Int) -> View,
  private val bindViewHolder: (viewHolder: ViewHolder, item: ITEM, position: Int) -> Unit
) : RecyclerView.Adapter<ViewHolder>() {

  var items: List<ITEM>
    set(value) {
      _items = value
    }
    get() {
      return _items.toList()
    }

  private var _items: List<ITEM> = emptyList()

  override fun getItemCount() = _items.size

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
    ViewHolder(createView.invoke(parent, viewType))

  override fun onBindViewHolder(holder: ViewHolder, position: Int) =
    bindViewHolder.invoke(holder, _items[position], position)

}

class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer