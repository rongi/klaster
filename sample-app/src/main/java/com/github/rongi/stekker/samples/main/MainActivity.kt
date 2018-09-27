package com.github.rongi.stekker.samples.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.github.rongi.stekker.SimpleAdapter
import com.github.rongi.stekker.Stekker
import com.github.rongi.stekker.samples.R
import com.github.rongi.stekker.samples.common.init
import com.github.rongi.stekker.samples.common.launch
import com.github.rongi.stekker.samples.common.onClick
import com.github.rongi.stekker.samples.examples.generic.GenericExampleActivity
import com.github.rongi.stekker.samples.examples.simple.SimpleExampleActivity
import kotlinx.android.synthetic.main.list_item.*
import kotlinx.android.synthetic.main.recycler_view_activity.*

class MainActivity : AppCompatActivity() {

  private lateinit var adapter: SimpleAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.recycler_view_activity)
    recycler_view.init(this)

    val items = listItems().toMutableList()

    adapter = Stekker.get()
      .itemCount(items.size)
      .view(R.layout.list_item, layoutInflater)
      .bind { position ->
        val item = items[position]
        item_text.text = item.name
        itemView.onClick = { onListItemClick(item.id) }
      }
      .build()

    recycler_view.adapter = adapter

    adapter.notifyDataSetChanged()
  }

  private fun onListItemClick(id: String) = when (id) {
    "simple" -> SimpleExampleActivity::class.launch(this)
    "generic" -> GenericExampleActivity::class.launch(this)
//    "stateful" -> StatefulExampleActivity::class.launch(this)
    else -> throw IllegalStateException("Unknown id: $id")
  }

  private fun listItems(): List<ExampleListItem> {
    return listOf(
      ExampleListItem(
        id = "simple",
        name = "Simple Example"
      ),
      ExampleListItem(
        id = "generic",
        name = "Generic Example"
      )
//      ExampleListItem(
//        id = "stateful",
//        name = "Stateful Example"
//      )
    )
  }

}

class ExampleListItem(
  val id: String,
  val name: String
)