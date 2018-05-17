package com.github.rongi.klaster.samples.examples.stateful

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.github.rongi.klaster.Klaster
import com.github.rongi.klaster.KlasterAdapter
import com.github.rongi.klaster.samples.R
import com.github.rongi.klaster.samples.common.*
import com.github.rongi.klaster.samples.examples.stateful.articleview.ArticleViewImpl
import com.github.rongi.klaster.samples.examples.stateful.articleview.ArticleViewPresenter
import com.github.rongi.klaster.samples.main.data.ArticlesProvider
import com.github.rongi.klaster.samples.main.model.Article
import kotlinx.android.synthetic.main.list_item.*
import kotlinx.android.synthetic.main.list_item.view.*
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

    presenter = StatefulExamplePresenter(
      view = this,
      articlesProvider = ArticlesProvider
    )
    presenter.onViewCreated()
  }

  override fun showArticles(articles: List<Article>) {
    // TODO-DMITRY should be something like "adapter.dataSet().setItems()"
    adapter.items = articles.map(::createArticleViewPresenter).toMutableList()
    adapter.notifyDataSetChanged()
  }

  override fun deleteArticle(articleIndex: Int) {
    adapter.items.removeAt(articleIndex)
    adapter.notifyItemRemoved(articleIndex)
  }

  override fun showToast(message: String) {
    message.toast(this)
  }

  private fun createArticleViewPresenter(it: Article) =
    ArticleViewPresenter(it).apply {
      onArticleClick = presenter::onArticleClick
      onArticleDeleteClick = presenter::onArticleDeleteClick
    }

  private fun createAdapter() = Klaster.of<ArticleViewPresenter>()
    .view(R.layout.list_item) {
      delete_button.visible = true
      check_box.visible = true
    }
    .bind { itemPresenter: ArticleViewPresenter ->
      itemPresenter.bindView(ArticleViewImpl(this))
      itemView.onClick = itemPresenter::onArticleClick
      delete_button.onClick = itemPresenter::onArticleDeleteClick
      check_box.onCheckedChanged = itemPresenter::onCheckedChanged
    }
    .layoutInflater(layoutInflater)
    .build()

}
