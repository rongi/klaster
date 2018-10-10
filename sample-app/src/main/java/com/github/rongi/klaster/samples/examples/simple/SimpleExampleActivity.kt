package com.github.rongi.klaster.samples.examples.simple

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import com.github.rongi.klaster.samples.R
import com.github.rongi.klaster.samples.common.init
import com.github.rongi.klaster.samples.common.onClick
import com.github.rongi.klaster.samples.common.toast
import com.github.rongi.klaster.samples.main.data.ArticlesProvider
import com.github.rongi.klaster.samples.main.model.Article
import com.github.rongi.stekker.Stekker
import kotlinx.android.synthetic.main.list_item.*
import kotlinx.android.synthetic.main.recycler_view_activity.*

class SimpleExampleActivity : AppCompatActivity(), SimpleExampleView {

  private lateinit var adapter: RecyclerView.Adapter<*>

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
