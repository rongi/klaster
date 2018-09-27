package com.github.rongi.stekker

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.github.rongi.stekker.test.test.R
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TestStekkerWithViewHolderBuilder {

  private val appContext = InstrumentationRegistry.getTargetContext()

  private val parent = FrameLayout(appContext)

  private val layoutInflater = LayoutInflater.from(appContext)

  @Test
  fun createsViewHolder() {
    val items = listOf(Article("article title"))

    val adapter = Stekker.withViewHolder<MyViewHolder>()
      .itemCount { items.size }
      .viewHolder { parent ->
        val view = layoutInflater.inflate(R.layout.list_item, parent, false)
        MyViewHolder(view)
      }
      .bind { position ->
        articleTitle.text = items[position].title
      }
      .build()

    val viewHolder = adapter.createViewHolder(parent, 0)

    viewHolder assertIs MyViewHolder::class.java
  }

  @Test
  fun itemCountWorks() {
    val items = listOf(Article("article1 title"), Article("article2 title"))

    val adapter = Stekker.withViewHolder<MyViewHolder>()
      .itemCount { items.size }
      .viewHolder { parent ->
        val view = layoutInflater.inflate(R.layout.list_item, parent, false)
        MyViewHolder(view)
      }
      .bind { position ->
        articleTitle.text = items[position].title
      }
      .build()

    adapter.itemCount assertEquals 2
  }

  @Test
  fun itemCountFromNumberWorks() {
    val adapter = Stekker.withViewHolder<MyViewHolder>()
      .itemCount(42)
      .viewHolder { parent ->
        val view = layoutInflater.inflate(R.layout.list_item, parent, false)
        MyViewHolder(view)
      }
      .bind { position ->
        articleTitle.text = "position${position + 1}"
      }
      .build()

    adapter.itemCount assertEquals 42
  }

}
