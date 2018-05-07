package com.github.rongi.klaster.samples.examples.simple

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.github.rongi.klaster.Klaster
import com.github.rongi.klaster.KlasterAdapter
import com.github.rongi.klaster.samples.R
import com.github.rongi.klaster.samples.common.init
import com.github.rongi.klaster.samples.common.onClick
import com.github.rongi.klaster.samples.common.toast
import com.github.rongi.klaster.samples.main.data.ArticlesProvider
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

    presenter = SimpleExamplePresenter(
      view = this,
      articlesProvider = ArticlesProvider
    )
    presenter.onViewCreated()
  }

  override fun showArticles(articles: List<Article>) {
    adapter.items = articles.toMutableList()
    adapter.notifyDataSetChanged()
  }

  override fun showToast(message: String) {
    message.toast(this)
  }

  private fun createAdapter() = Klaster.of<Article>()
    .view(R.layout.list_item)
    .bind { article: Article ->
      item_text.text = article.title
      itemView.onClick = { presenter.onArticleClick(article) }
    }
    .layoutInflater(layoutInflater)
    .build()

}
