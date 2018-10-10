package com.github.rongi.klaster

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.github.rongi.klaster.test.test.R

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
  val articleTitle: TextView = itemView.findViewById(R.id.item_text)
}
