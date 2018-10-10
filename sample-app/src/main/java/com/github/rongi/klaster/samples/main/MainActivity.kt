package com.github.rongi.klaster.samples.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import com.github.rongi.stekker.Stekker
import com.github.rongi.klaster.samples.R
import com.github.rongi.klaster.samples.common.init
import com.github.rongi.klaster.samples.common.launch
import com.github.rongi.klaster.samples.common.onClick
import com.github.rongi.klaster.samples.examples.generic.GenericExampleActivity
import com.github.rongi.klaster.samples.examples.simple.SimpleExampleActivity
import kotlinx.android.synthetic.main.list_item.*
import kotlinx.android.synthetic.main.recycler_view_activity.*

class MainActivity : AppCompatActivity() {

  private lateinit var adapter: RecyclerView.Adapter<*>

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