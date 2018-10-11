package com.github.rongi.klaster.samples.examples.verbose

import android.view.LayoutInflater
import com.github.rongi.klaster.Klaster
import com.github.rongi.klaster.samples.R
import com.github.rongi.klaster.samples.main.data.ArticlesProvider
import kotlinx.android.synthetic.main.list_item.*

val articles = ArticlesProvider.getArticles()

private const val ITEM_TYPE_1 = 0
private const val ITEM_TYPE_2 = 1

fun createAdapter(layoutInflater: LayoutInflater) = Klaster.get()
  .itemCount { articles.size }
  .getItemViewType { position -> position % 2 }
  .view { viewType, parent ->
    when (viewType) {
      ITEM_TYPE_1 -> layoutInflater.inflate(R.layout.list_item1, parent, false)
      ITEM_TYPE_2 -> layoutInflater.inflate(R.layout.list_item2, parent, false)
      else -> throw IllegalStateException("Unknown type: $viewType")
    }
  }
  .bind { position ->
    val article = articles[position]
    item_text.text = article.title
  }
  .bind { position, payloads -> }
  .getItemId { position -> position.toLong() }
  .setHasStableIds { }
  .onAttachedToRecyclerView { }
  .onDetachedFromRecyclerView { }
  .registerAdapterDataObserver { }
  .unregisterAdapterDataObserver { }
  .onFailedToRecycleView { true }
  .onViewAttachedToWindow { }
  .onViewDetachedFromWindow { }
  .onViewRecycled { }
  .build()
