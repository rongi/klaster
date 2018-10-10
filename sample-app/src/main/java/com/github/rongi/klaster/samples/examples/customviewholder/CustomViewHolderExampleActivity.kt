package com.github.rongi.klaster.samples.examples.customviewholder

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import com.github.rongi.klaster.Klaster
import com.github.rongi.klaster.samples.R
import com.github.rongi.klaster.samples.common.init
import com.github.rongi.klaster.samples.common.onClick
import com.github.rongi.klaster.samples.common.toast
import com.github.rongi.klaster.samples.main.data.ArticlesProvider
import com.github.rongi.klaster.samples.main.model.Article
import kotlinx.android.synthetic.main.recycler_view_activity.*

class CustomViewHolderExampleActivity : AppCompatActivity(), CustomViewHolderExampleView {

  private lateinit var adapter: RecyclerView.Adapter<*>

  private lateinit var presenter: CustomViewHolderExamplePresenter

  private var articles: List<Article> = emptyList()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.recycler_view_activity)
    recycler_view.init(this)

    adapter = createAdapter()

    recycler_view.adapter = adapter

    presenter = CustomViewHolderExamplePresenter(
      view = this,
      articlesProvider = ArticlesProvider
    )
    presenter.onViewCreated()
  }

  private fun createAdapter() = Klaster.withViewHolder<MyViewHolder>()
    .itemCount { articles.size }
    .viewHolder { _, parent ->
      val view = layoutInflater.inflate(R.layout.list_item, parent, false)
      MyViewHolder(view)
    }
    .bind { position ->
      val article = articles[position]
      articleTitle.text = article.title
      itemView.onClick = { presenter.onArticleClick(article) }
    }
    .build()

  override fun showArticles(articles: List<Article>) {
    this.articles = articles
    adapter.notifyDataSetChanged()
  }

  override fun showToast(message: String) {
    message.toast(this)
  }

}

