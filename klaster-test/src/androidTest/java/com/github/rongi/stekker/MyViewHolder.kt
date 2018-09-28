package com.github.rongi.stekker

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.nhaarman.mockitokotlin2.mock

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
  val articleTitle: TextView = mock()
}
