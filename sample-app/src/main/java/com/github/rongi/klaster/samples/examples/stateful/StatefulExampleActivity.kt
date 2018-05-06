package com.github.rongi.klaster.samples.examples.stateful

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.github.rongi.klaster.Klaster
import com.github.rongi.klaster.KlasterAdapter
import com.github.rongi.klaster.samples.R
import com.github.rongi.klaster.samples.common.init
import com.github.rongi.klaster.samples.common.onClick
import com.github.rongi.klaster.samples.common.toast
import com.github.rongi.klaster.samples.examples.stateful.articleview.ArticleViewImpl
import com.github.rongi.klaster.samples.examples.stateful.articleview.ArticleViewPresenter
import com.github.rongi.klaster.samples.main.model.Article
import kotlinx.android.synthetic.main.recycler_view_activity.*

class StatefulExampleActivity : AppCompatActivity(), StatefulExampleView {

  private lateinit var adapter: KlasterAdapter<ArticleViewPresenter>

  private lateinit var presenter: StatefulExamplePresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.recycler_view_activity)
    recycler_view.init(this)

    adapter = createAdapter()

    recycler_view.adapter = adapter

    presenter = StatefulExamplePresenter(this)
    presenter.onViewCreated()
  }

  override fun showArticles(articles: List<Article>) {
    adapter.items = articles.map {
      ArticleViewPresenter(it).apply { onArticleClickListener = presenter::onArticleClick }
    }
    adapter.notifyDataSetChanged()
  }

  override fun showToast(message: String) {
    message.toast(this)
  }

  private fun createAdapter() = Klaster.of<ArticleViewPresenter>()
    .view(R.layout.list_item)
    .bind { presenter: ArticleViewPresenter ->
      presenter.setView(ArticleViewImpl(this))
      itemView.onClick = presenter::onArticleClick
    }
    .layoutInflater(layoutInflater)
    .build()

}
