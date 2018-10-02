package com.github.rongi.stekker

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import com.github.rongi.stekker.test.test.R
import com.nhaarman.mockitokotlin2.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers

@RunWith(AndroidJUnit4::class)
class TestStekkerWithViewHolderBuilder {

  private val appContext = InstrumentationRegistry.getTargetContext()

  private val parent = FrameLayout(appContext)

  private val layoutInflater = LayoutInflater.from(appContext)

  private val viewMock: View = mock()

  private val viewHolderMock = MyViewHolder(viewMock)

  private val recyclerViewMock: RecyclerView = mock()

  private val adapterDataObserverMock: RecyclerView.AdapterDataObserver = mock()

  @Test
  fun createsViewHolder() {
    val items = listOf(Article("article title"))

    val adapter = Stekker.withViewHolder<MyViewHolder>()
      .itemCount { items.size }
      .viewHolder { _, parent ->
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
  fun createsViewHolderWithViewType() {
    val items = listOf(Article("article title"))

    val adapter = Stekker.withViewHolder<MyViewHolder>()
      .itemCount { items.size }
      .viewHolder { viewType: Int, parent: ViewGroup ->
        when (viewType) {
          TYPE1 -> MyViewHolder(TextView(appContext).apply {
            text = "type 1"
          })
          TYPE2 -> MyViewHolder(TextView(appContext).apply {
            text = "type 2"
          })
          else -> throw IllegalStateException("Unknown view type $viewType")
        }
      }
      .bind { _ -> }
      .build()

    val viewHolder1 = adapter.createViewHolder(parent, TYPE1)
    val viewHolder2 = adapter.createViewHolder(parent, TYPE2)

    viewHolder1.itemView.cast<TextView>().text assertEquals "type 1"
    viewHolder2.itemView.cast<TextView>().text assertEquals "type 2"
  }

  @Test
  fun itemCountWorks() {
    val items = listOf(Article("article1 title"), Article("article2 title"))

    val adapter = Stekker.withViewHolder<MyViewHolder>()
      .itemCount { items.size }
      .defaultViewHolder(layoutInflater)
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
      .defaultViewHolder(layoutInflater)
      .bind { position ->
        articleTitle.text = "position${position + 1}"
      }
      .build()

    adapter.itemCount assertEquals 42
  }

  @Test
  fun getItemIdWorks() {
    val adapter = Stekker.withViewHolder<MyViewHolder>()
      .itemCount(42)
      .defaultViewHolder(layoutInflater)
      .defaultBind()
      .getItemId { position -> position * 2L }
      .build()

    adapter.getItemId(10) assertEquals 20L
  }

  @Test
  fun bindWithPayloadsWorks() {
    val mockFunction = mock<((binder: MyViewHolder, position: Int, payloads: MutableList<Any>) -> Unit)>()
    val adapter = Stekker.withViewHolder<MyViewHolder>()
      .itemCount(100)
      .defaultViewHolder(layoutInflater)
      .defaultBind()
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
    val adapter = Stekker.withViewHolder<MyViewHolder>()
      .itemCount(100)
      .defaultViewHolder(layoutInflater)
      .defaultBind()
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
    val adapter = Stekker.withViewHolder<MyViewHolder>()
      .itemCount(100)
      .defaultViewHolder(layoutInflater)
      .defaultBind()
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
    val adapter = Stekker.withViewHolder<MyViewHolder>()
      .itemCount(100)
      .defaultViewHolder(layoutInflater)
      .defaultBind()
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
    val adapter = Stekker.withViewHolder<MyViewHolder>()
      .itemCount(100)
      .defaultViewHolder(layoutInflater)
      .defaultBind()
      .onDetachedFromRecyclerView { recyclerView ->
        mockFunction(recyclerView)
      }
      .build()

    adapter.onDetachedFromRecyclerView(recyclerViewMock)

    verify(mockFunction).invoke(recyclerViewMock)
  }

  @Test
  fun onViewAttachedToWindowWorks() {
    val mockFunction = mock<((holder: MyViewHolder) -> Unit)>()
    val adapter = Stekker.withViewHolder<MyViewHolder>()
      .itemCount(100)
      .defaultViewHolder(layoutInflater)
      .defaultBind()
      .onViewAttachedToWindow { holder ->
        mockFunction(holder)
      }
      .build()

    adapter.onViewAttachedToWindow(viewHolderMock)

    verify(mockFunction).invoke(viewHolderMock)
  }

  @Test
  fun onViewDetachedFromWindowWorks() {
    val mockFunction = mock<((holder: MyViewHolder) -> Unit)>()
    val adapter = Stekker.withViewHolder<MyViewHolder>()
      .itemCount(100)
      .defaultViewHolder(layoutInflater)
      .defaultBind()
      .onViewDetachedFromWindow { holder ->
        mockFunction(holder)
      }
      .build()

    adapter.onViewDetachedFromWindow(viewHolderMock)

    verify(mockFunction).invoke(viewHolderMock)
  }

  @Test
  fun onFailedToRecycleViewWorks() {
    val mockFunction = mock<((holder: MyViewHolder) -> Boolean)>().apply {
      whenever(this.invoke(anyOrNull())).thenReturn(true)
    }
    val adapter = Stekker.withViewHolder<MyViewHolder>()
      .itemCount(100)
      .defaultViewHolder(layoutInflater)
      .defaultBind()
      .onFailedToRecycleView { holder ->
        mockFunction(holder)
      }
      .build()

    adapter.onFailedToRecycleView(viewHolderMock)

    verify(mockFunction).invoke(viewHolderMock)
  }

  @Test
  fun onViewRecycledWorks() {
    val mockFunction = mock<((holder: MyViewHolder) -> Unit)>()
    val adapter = Stekker.withViewHolder<MyViewHolder>()
      .itemCount(100)
      .defaultViewHolder(layoutInflater)
      .defaultBind()
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
    val adapter = Stekker.withViewHolder<MyViewHolder>()
      .itemCount(100)
      .defaultViewHolder(layoutInflater)
      .defaultBind()
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
    val adapter = Stekker.withViewHolder<MyViewHolder>()
      .itemCount(100)
      .defaultViewHolder(layoutInflater)
      .defaultBind()
      .unregisterAdapterDataObserver { observer ->
        mockFunction(observer)
      }
      .build()

    adapter.unregisterAdapterDataObserver(adapterDataObserverMock)

    verify(mockFunction).invoke(adapterDataObserverMock)
  }

}

private fun StekkerWithViewHolderBuilder<MyViewHolder>.defaultViewHolder(layoutInflater: LayoutInflater): StekkerWithViewHolderBuilder<MyViewHolder> {
  return this.viewHolder { _, parent ->
    val view = layoutInflater.inflate(R.layout.list_item, parent, false)
    MyViewHolder(view)
  }
}

private fun StekkerWithViewHolderBuilder<MyViewHolder>.defaultBind(): StekkerWithViewHolderBuilder<MyViewHolder> {
  return bind { position ->
    articleTitle.text = "position${position + 1}"
  }
}
