package com.github.rongi.stekker

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.github.rongi.stekker.test.test.R
import com.nhaarman.mockitokotlin2.*
import kotlinx.android.synthetic.main.list_item.*
import kotlinx.android.synthetic.main.list_item.view.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers

@RunWith(AndroidJUnit4::class)
class TestStekkerBuilder {

  private val appContext = InstrumentationRegistry.getTargetContext()

  private val parent = FrameLayout(appContext)

  private val layoutInflater = LayoutInflater.from(appContext)

  private val viewMock: View = mock()

  private val viewHolderMock = SimpleViewHolder(viewMock)

  private val recyclerViewMock: RecyclerView = mock()

  private val adapterDataObserverMock: RecyclerView.AdapterDataObserver = mock()

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

  @Test
  fun bindWithPayloadsWorks() {
    val mockFunction = mock<((binder: SimpleViewHolder, position: Int, payloads: MutableList<Any>) -> Unit)>()
    val adapter = Stekker.get()
      .itemCount(100)
      .view(R.layout.list_item, layoutInflater)
      .bind { position -> item_text.text = "$position" }
      .bind { position, payloads ->
        mockFunction(this, position, payloads)
      }
      .build()

    adapter.onBindViewHolder(viewHolderMock, 42, listOf("a"))

    verify(mockFunction).invoke(any(), eq(42), eq(mutableListOf<Any>("a")))
  }

  @Test
  fun getItemViewTypeWorks() {
    val mockFunction = mock<((Int) -> Int)>().apply {
      whenever(this.invoke(ArgumentMatchers.anyInt())).thenReturn(11)
    }
    val adapter = Stekker.get()
      .itemCount(100)
      .view(R.layout.list_item, layoutInflater)
      .bind { position -> item_text.text = "$position" }
      .getItemViewType { position ->
        mockFunction(position)
      }
      .build()

    adapter.getItemViewType(42)

    verify(mockFunction).invoke(42)
  }

  @Test
  fun setHasStableIdsWorks() {
    val mockFunction = mock<(() -> Unit)>()
    val adapter = Stekker.get()
      .itemCount(100)
      .view(R.layout.list_item, layoutInflater)
      .bind { position -> item_text.text = "$position" }
      .setHasStableIds {
        mockFunction()
      }
      .build()

    adapter.setHasStableIds(true)

    verify(mockFunction).invoke()
  }

  @Test
  fun onAttachedToRecyclerViewWorks() {
    val mockFunction = mock<((recyclerView: RecyclerView) -> Unit)>()
    val adapter = Stekker.get()
      .itemCount(100)
      .view(R.layout.list_item, layoutInflater)
      .bind { position -> item_text.text = "$position" }
      .onAttachedToRecyclerView { recyclerView ->
        mockFunction(recyclerView)
      }
      .build()

    adapter.onAttachedToRecyclerView(recyclerViewMock)

    verify(mockFunction).invoke(recyclerViewMock)
  }

  @Test
  fun onDetachedFromRecyclerViewWorks() {
    val mockFunction = mock<((recyclerView: RecyclerView) -> Unit)>()
    val adapter = Stekker.get()
      .itemCount(100)
      .view(R.layout.list_item, layoutInflater)
      .bind { position -> item_text.text = "$position" }
      .onDetachedFromRecyclerView { recyclerView ->
        mockFunction(recyclerView)
      }
      .build()

    adapter.onDetachedFromRecyclerView(recyclerViewMock)

    verify(mockFunction).invoke(recyclerViewMock)
  }

  @Test
  fun onViewAttachedToWindowWorks() {
    val mockFunction = mock<((holder: SimpleViewHolder) -> Unit)>()
    val adapter = Stekker.get()
      .itemCount(100)
      .view(R.layout.list_item, layoutInflater)
      .bind { position -> item_text.text = "$position" }
      .onViewAttachedToWindow { holder ->
        mockFunction(holder)
      }
      .build()

    adapter.onViewAttachedToWindow(viewHolderMock)

    verify(mockFunction).invoke(viewHolderMock)
  }

  @Test
  fun onViewDetachedFromWindowWorks() {
    val mockFunction = mock<((holder: SimpleViewHolder) -> Unit)>()
    val adapter = Stekker.get()
      .itemCount(100)
      .view(R.layout.list_item, layoutInflater)
      .bind { position -> item_text.text = "$position" }
      .onViewDetachedFromWindow { holder ->
        mockFunction(holder)
      }
      .build()

    adapter.onViewDetachedFromWindow(viewHolderMock)

    verify(mockFunction).invoke(viewHolderMock)
  }

  @Test
  fun onFailedToRecycleViewWorks() {
    val mockFunction = mock<((holder: SimpleViewHolder) -> Boolean)>().apply {
      whenever(this.invoke(anyOrNull())).thenReturn(true)
    }
    val adapter = Stekker.get()
      .itemCount(100)
      .view(R.layout.list_item, layoutInflater)
      .bind { position -> item_text.text = "$position" }
      .onFailedToRecycleView { holder ->
        mockFunction(holder)
      }
      .build()

    adapter.onFailedToRecycleView(viewHolderMock)

    verify(mockFunction).invoke(viewHolderMock)
  }

  @Test
  fun onViewRecycledWorks() {
    val mockFunction = mock<((holder: SimpleViewHolder) -> Unit)>()
    val adapter = Stekker.get()
      .itemCount(100)
      .view(R.layout.list_item, layoutInflater)
      .bind { position -> item_text.text = "$position" }
      .onViewRecycled { holder ->
        mockFunction(holder)
      }
      .build()

    adapter.onViewRecycled(viewHolderMock)

    verify(mockFunction).invoke(viewHolderMock)
  }

  @Test
  fun registerAdapterDataObserverWorks() {
    val mockFunction = mock<((observer: RecyclerView.AdapterDataObserver) -> Unit)>()
    val adapter = Stekker.get()
      .itemCount(100)
      .view(R.layout.list_item, layoutInflater)
      .bind { position -> item_text.text = "$position" }
      .registerAdapterDataObserver { observer ->
        mockFunction(observer)
      }
      .build()

    adapter.registerAdapterDataObserver(adapterDataObserverMock)

    verify(mockFunction).invoke(adapterDataObserverMock)
  }

  @Test
  fun unregisterAdapterDataObserverWorks() {
    val mockFunction = mock<((observer: RecyclerView.AdapterDataObserver) -> Unit)>()
    val adapter = Stekker.get()
      .itemCount(100)
      .view(R.layout.list_item, layoutInflater)
      .bind { position -> item_text.text = "$position" }
      .unregisterAdapterDataObserver { observer ->
        mockFunction(observer)
      }
      .build()

    adapter.unregisterAdapterDataObserver(adapterDataObserverMock)

    verify(mockFunction).invoke(adapterDataObserverMock)
  }

}
