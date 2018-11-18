package com.github.rongi.klaster.samples.examples.multipleviewtypes

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import com.github.rongi.klaster.Klaster
import com.github.rongi.klaster.samples.R
import com.github.rongi.klaster.samples.common.init
import com.github.rongi.klaster.samples.common.onClick
import com.github.rongi.klaster.samples.common.toast
import com.github.rongi.klaster.samples.main.data.ArticlesProvider
import kotlinx.android.synthetic.main.header.*
import kotlinx.android.synthetic.main.list_item.*
import kotlinx.android.synthetic.main.recycler_view_activity.*

class MultipleViewTypesExampleActivity : AppCompatActivity(), MultipleViewTypesExampleView {

  private lateinit var adapter: RecyclerView.Adapter<*>

  private lateinit var presenter: MultipleViewTypesExamplePresenter

  private var listItems: List<ListItemViewData> = emptyList()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.recycler_view_activity)
    recycler_view.init(this)

    adapter = createAdapter()

    recycler_view.adapter = adapter

    presenter = MultipleViewTypesExamplePresenter(
      view = this,
      articlesProvider = ArticlesProvider
    )
    presenter.onViewCreated()
  }

  override fun showListItems(listItems: List<ListItemViewData>) {
    this.listItems = listItems
    adapter.notifyDataSetChanged()
  }

  override fun showToast(message: String) {
    message.toast(this)
  }

  private fun createAdapter() = Klaster.get()
    .itemCount { listItems.size }
    .getItemViewType { position ->
      when (listItems[position]) {
        is ArticleViewData -> 0
        is HeaderViewData -> 1
      }
    }
    .view { viewType, parent ->
      when (viewType) {
        0 -> layoutInflater.inflate(R.layout.list_item, parent, false)
        1 -> layoutInflater.inflate(R.layout.header, parent, false)
        else -> throw IllegalStateException("Unknown view type: $viewType")
      }
    }
    .bind { position ->
      val listItem = listItems[position]

      when (listItem) {
        is ArticleViewData -> {
          item_text.text = listItem.article.title
          itemView.onClick = { presenter.onArticleClick(listItem.article) }
        }
        is HeaderViewData -> {
          header_text.text = listItem.headerText
        }
      }
    }
    .build()

}
