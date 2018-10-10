package com.github.rongi.klaster.samples.examples.functional

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import com.github.rongi.klaster.Klaster
import com.github.rongi.klaster.samples.R
import com.github.rongi.klaster.samples.common.init
import com.github.rongi.klaster.samples.common.onClick
import com.github.rongi.klaster.samples.common.toast
import com.github.rongi.klaster.samples.main.data.ArticlesProvider
import com.github.rongi.klaster.samples.main.model.Article
import kotlinx.android.synthetic.main.list_item.*
import kotlinx.android.synthetic.main.recycler_view_activity.*

class FunctionalExampleActivity : AppCompatActivity(), FunctionalExampleView {

  private lateinit var presenter: FunctionalExamplePresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.recycler_view_activity)
    recycler_view.init(this)

    val (adapter, listPresenter) = createAdapter(
      layoutInflater = layoutInflater,
      onItemClick = { presenter.onArticleClick(it) }
    )

    recycler_view.adapter = adapter

    presenter = FunctionalExamplePresenter(
      view = this,
      articlesProvider = ArticlesProvider,
      listPresenter = listPresenter
    )
    presenter.onViewCreated()
  }

  override fun showToast(message: String) {
    message.toast(this)
  }

}

private fun createAdapter(
  layoutInflater: LayoutInflater,
  onItemClick: (Article) -> Unit
): Pair<RecyclerView.Adapter<*>, ListPresenter> {
  var articles: List<Article> = emptyList()

  val adapter = Klaster.get()
    .itemCount { articles.size }
    .view(R.layout.list_item, layoutInflater)
    .bind { position ->
      val article = articles[position]
      item_text.text = article.title
      itemView.onClick = { onItemClick(article) }
    }
    .build()

  val presenter = object : ListPresenter {
    override fun setItems(items: List<Article>) {
      articles = items
      adapter.notifyDataSetChanged()
    }
  }

  return adapter to presenter
}
