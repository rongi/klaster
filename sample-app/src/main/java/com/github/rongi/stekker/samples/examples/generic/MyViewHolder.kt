package com.github.rongi.stekker.samples.examples.generic

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.github.rongi.stekker.samples.R.id

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
  val articleTitle: TextView = itemView.findViewById(id.item_text)
}