package com.github.rongi.klaster.samples.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.github.rongi.klaster.Box
import com.github.rongi.klaster.BoxAdapter
import com.github.rongi.klaster.samples.R
import com.github.rongi.klaster.samples.common.init
import com.github.rongi.klaster.samples.common.launch
import com.github.rongi.klaster.samples.common.onClick
import com.github.rongi.klaster.samples.examples.SimpleExampleActivity
import kotlinx.android.synthetic.main.list_item.*
import kotlinx.android.synthetic.main.recycler_view_activity.*

class MainActivity : AppCompatActivity() {

  private lateinit var adapter: BoxAdapter<ExampleListItem>

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.recycler_view_activity)
    recycler_view.init(this)

    adapter = Box.of<ExampleListItem>()
      .view(R.layout.list_item)
      .bind { item, position ->
        item_text.text = item.name
        itemView.onClick = item.onClick
      }
      .layoutInflater(layoutInflater)
      .build()

    recycler_view.adapter = adapter

    adapter.items = listItems()
    adapter.notifyDataSetChanged()
  }

  private fun listItems(): List<ExampleListItem> {
    return listOf(
      ExampleListItem(
        name = "Simple Example",
        onClick = { SimpleExampleActivity::class.launch(this) }
      )
    )
  }

}

class ExampleListItem(
  val name: String,
  val onClick: () -> Unit
)