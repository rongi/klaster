package com.github.rongi.stekker.samples.examples.generic

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import com.github.rongi.stekker.Stekker
import com.github.rongi.stekker.samples.R
import com.github.rongi.stekker.samples.common.init
import com.github.rongi.stekker.samples.common.onClick
import com.github.rongi.stekker.samples.common.toast
import com.github.rongi.stekker.samples.main.data.ArticlesProvider
import com.github.rongi.stekker.samples.main.model.Article
import kotlinx.android.synthetic.main.recycler_view_activity.*

class GenericExampleActivity : AppCompatActivity(), GenericExampleView {

  private lateinit var adapter: RecyclerView.Adapter<*>

  private lateinit var presenter: GenericExamplePresenter

  private var articles: List<Article> = emptyList()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.recycler_view_activity)
    recycler_view.init(this)

    adapter = createAdapter()

    recycler_view.adapter = adapter

    presenter = GenericExamplePresenter(
      view = this,
      articlesProvider = ArticlesProvider
    )
    presenter.onViewCreated()
  }

  private fun createAdapter() = Stekker.withViewHolder<MyViewHolder>()
    .itemCount { articles.size }
    .viewHolder { parent ->
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

