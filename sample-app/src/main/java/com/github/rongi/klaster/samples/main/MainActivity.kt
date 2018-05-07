package com.github.rongi.klaster.samples.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.github.rongi.klaster.Klaster
import com.github.rongi.klaster.KlasterAdapter
import com.github.rongi.klaster.samples.R
import com.github.rongi.klaster.samples.common.init
import com.github.rongi.klaster.samples.common.launch
import com.github.rongi.klaster.samples.common.onClick
import com.github.rongi.klaster.samples.examples.simple.SimpleExampleActivity
import com.github.rongi.klaster.samples.examples.stateful.StatefulExampleActivity
import kotlinx.android.synthetic.main.list_item.*
import kotlinx.android.synthetic.main.recycler_view_activity.*

class MainActivity : AppCompatActivity() {

  private lateinit var adapter: KlasterAdapter<ExampleListItem>

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.recycler_view_activity)
    recycler_view.init(this)

    adapter = Klaster.of<ExampleListItem>()
      .view(R.layout.list_item)
      .bind { item ->
        item_text.text = item.name
        itemView.onClick = { onListItemClick(item.id) }
      }
      .layoutInflater(layoutInflater)
      .build()

    recycler_view.adapter = adapter

    adapter.items = listItems().toMutableList()
    adapter.notifyDataSetChanged()
  }

  private fun onListItemClick(id: String) = when (id) {
    "simple" -> SimpleExampleActivity::class.launch(this)
    "stateful" -> StatefulExampleActivity::class.launch(this)
    else -> throw IllegalStateException("Unknown id: $id")
  }

  private fun listItems(): List<ExampleListItem> {
    return listOf(
      ExampleListItem(
        id = "simple",
        name = "Simple Example"
      ),
      ExampleListItem(
        id = "stateful",
        name = "Stateful Example"
      )
    )
  }

}

class ExampleListItem(
  val id: String,
  val name: String
)