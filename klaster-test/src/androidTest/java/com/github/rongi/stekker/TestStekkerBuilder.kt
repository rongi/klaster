package com.github.rongi.stekker

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.TextView
import com.github.rongi.stekker.test.test.R
import kotlinx.android.synthetic.main.list_item.*
import kotlinx.android.synthetic.main.list_item.view.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TestStekkerBuilder {

  private val appContext = InstrumentationRegistry.getTargetContext()

  private val parent = FrameLayout(appContext)

  private val layoutInflater = LayoutInflater.from(appContext)

  @Test
  fun createsViewFromResourceId() {
    val items = listOf(Article("article title"))

    val adapter = Stekker.get()
      .itemCount { items.size }
      .view(R.layout.list_item, layoutInflater)
      .bind { position ->
        item_text.text = items[position].title
      }
      .build()

    val viewHolder = adapter.createViewHolder(parent, 0)

    viewHolder.itemView.item_text assertIs TextView::class.java
  }

  @Test
  fun createsViewFromFunction() {
    val items = listOf(Article("article title"))

    val adapter = Stekker.get()
      .itemCount { items.size }
      .view {
        TextView(appContext)
      }
      .bind { position ->
        (itemView as TextView).text = items[position].title
      }
      .build()

    val viewHolder = adapter.createViewHolder(parent, 0).apply {
      adapter.bindViewHolder(this, 0)
    }

    viewHolder.itemView assertIs TextView::class.java
  }

  @Test
  fun createsViewFromResourceIdWithInitFunction() {
    val items = listOf(Article("article title"))

    val adapter = Stekker.get()
      .itemCount { items.size }
      .view(R.layout.list_item, layoutInflater) {
        this.item_text.error = "error message"
      }
      .bind { position ->
        item_text.text = items[position].title
      }
      .build()

    val viewHolder = adapter.createViewHolder(parent, 0)

    viewHolder.itemView.item_text assertIs TextView::class.java
    (viewHolder.itemView.item_text as TextView).error assertEquals "error message"
  }

  @Test
  fun createsViewFromFunctionWithParent() {
    val items = listOf(Article("article title"))

    val adapter = Stekker.get()
      .itemCount { items.size }
      .viewWithParent { parent ->
        LayoutInflater.from(appContext).inflate(R.layout.list_item, parent, false)
      }
      .bind { position ->
        item_text.text = items[position].title
      }
      .build()

    val viewHolder = adapter.createViewHolder(parent, 0)

    viewHolder.itemView.layoutParams assertIs FrameLayout.LayoutParams::class.java
  }

  @Test
  fun bindsView() {
    val items = listOf(Article("article title"))

    val adapter = Stekker.get()
      .itemCount { items.size }
      .view(R.layout.list_item, layoutInflater)
      .bind { position ->
        item_text.text = items[position].title
      }
      .build()

    val viewHolder = adapter.createViewHolder(parent, 0).apply {
      adapter.bindViewHolder(this, 0)
    }

    (viewHolder.itemView.item_text as TextView).text assertEquals "article title"
  }

  @Test
  fun bindsViewWithPosition() {
    val items = listOf(Article("article"))

    val adapter = Stekker.get()
      .itemCount { items.size }
      .view(R.layout.list_item, layoutInflater)
      .bind { position ->
        item_text.text = "${items[position].title} ${position + 1}"
      }
      .build()

    val viewHolder = adapter.createViewHolder(parent, 0).apply {
      adapter.bindViewHolder(this, 0)
    }

    (viewHolder.itemView.item_text as TextView).text assertEquals "article 1"
  }

  @Test
  fun itemCountWorks() {
    val items = listOf(Article("article1 title"), Article("article2 title"))

    val adapter = Stekker.get()
      .itemCount { items.size }
      .view(R.layout.list_item, layoutInflater)
      .bind { position ->
        item_text.text = items[position].title
      }
      .build()

    adapter.itemCount assertEquals 2
  }

  @Test
  fun itemCountFromNumberWorks() {
    val adapter = Stekker.get()
      .itemCount(42)
      .view(R.layout.list_item, layoutInflater)
      .bind { position -> item_text.text = "$position" }
      .build()

    adapter.itemCount assertEquals 42
  }

  @Test
  fun getItemIdWorks() {
    val adapter = Stekker.get()
      .itemCount(42)
      .view(R.layout.list_item, layoutInflater)
      .bind { position -> item_text.text = "$position" }
      .getItemId { position -> position * 2L }
      .build()

    adapter.getItemId(10) assertEquals 20L
  }

}
