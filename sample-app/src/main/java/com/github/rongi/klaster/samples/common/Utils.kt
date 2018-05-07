package com.github.rongi.klaster.samples.common

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import java.lang.UnsupportedOperationException
import kotlin.reflect.KClass

fun <T : Activity> KClass<T>.launch(from: Activity) {
  val intent = Intent(from, this.java)
  from.startActivity(intent)
}

fun RecyclerView.init(context: Context) {
  layoutManager = LinearLayoutManager(context)
  addItemDecoration(DividerItemDecoration(context.resources))
}

fun Any.toast(context: Context, duration: Int = Toast.LENGTH_SHORT): Toast {
  return Toast.makeText(context, this.toString(), duration).apply { show() }
}

inline var View.onClick: () -> Unit
  set(crossinline value) = setOnClickListener { value() }
  get() {
    throw UnsupportedOperationException()
  }

var View.visible: Boolean
  get() {
    return this.visibility == View.GONE
  }
  set(visible) {
    this.visibility = if (visible) View.VISIBLE else View.GONE
  }
