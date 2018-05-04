package com.github.rongi.klaster.samples.examples

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.github.rongi.klaster.Klaster
import com.github.rongi.klaster.KlasterAdapter
import com.github.rongi.klaster.samples.R
import com.github.rongi.klaster.samples.common.init
import com.github.rongi.klaster.samples.common.onClick
import com.github.rongi.klaster.samples.common.toast
import com.github.rongi.klaster.samples.main.model.Article
import kotlinx.android.synthetic.main.list_item.*
import kotlinx.android.synthetic.main.recycler_view_activity.*

class SimpleExampleActivity : AppCompatActivity(), SimpleExampleView {

  private lateinit var adapter: KlasterAdapter<Article>

  private lateinit var presenter: SimpleExamplePresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.recycler_view_activity)
    recycler_view.init(this)

    adapter = createAdapter()

    recycler_view.adapter = adapter

    presenter = SimpleExamplePresenter(this)
    presenter.onViewCreated()
  }

  override fun showArticles(articles: List<Article>) {
    // TODO-DMITRY should items be contained inside?
    adapter.items = articles
    adapter.notifyDataSetChanged()
  }

  private fun createAdapter() = Klaster.of<Article>()
    .view(R.layout.list_item)
    .bind { article: Article ->
      item_text.text = article.title
      itemView.onClick = {
        // TODO-DMITRY find out how to handle
        "Click: ${article.title}".toast(this@SimpleExampleActivity)
      }
    }
    .layoutInflater(layoutInflater)
    .build()

}
