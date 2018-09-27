package com.github.rongi.stekker.samples.examples.simple

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.github.rongi.stekker.SimpleAdapter
import com.github.rongi.stekker.Stekker
import com.github.rongi.stekker.samples.R
import com.github.rongi.stekker.samples.common.init
import com.github.rongi.stekker.samples.common.onClick
import com.github.rongi.stekker.samples.common.toast
import com.github.rongi.stekker.samples.main.data.ArticlesProvider
import com.github.rongi.stekker.samples.main.model.Article
import kotlinx.android.synthetic.main.list_item.*
import kotlinx.android.synthetic.main.recycler_view_activity.*

class SimpleExampleActivity : AppCompatActivity(), SimpleExampleView {

  private lateinit var adapter: SimpleAdapter

  private lateinit var presenter: SimpleExamplePresenter

  private var articles: List<Article> = emptyList()

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
    this.articles = articles
    adapter.notifyDataSetChanged()
  }

  override fun showToast(message: String) {
    message.toast(this)
  }

  private fun createAdapter() = Stekker.get()
    .itemCount { articles.size }
    .view(R.layout.list_item, layoutInflater)
    .bind { position ->
      val article = articles[position]
      item_text.text = article.title
      itemView.onClick = { presenter.onArticleClick(article) }
    }
    .build()

}
