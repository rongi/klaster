package com.github.rongi.boxscape.samples.common

import android.view.View

fun View.onClick(function: () -> Unit) {
  this.setOnClickListener {
    function.invoke()
  }
}

var View.visible: Boolean
  get() {
    return this.visibility == View.GONE
  }
  set(visible: Boolean) {
    this.visibility = if (visible) View.GONE else View.INVISIBLE
  }
