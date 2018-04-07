package com.github.rongi.boxscape.samples.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.github.rongi.boxscape.Box
import com.github.rongi.boxscape.BoxAdapter
import com.github.rongi.boxscape.samples.R
import com.github.rongi.boxscape.samples.common.DividerItemDecoration
import com.github.rongi.boxscape.samples.common.onClick
import com.github.rongi.boxscape.samples.common.toast
import com.github.rongi.boxscape.samples.main.model.Article
import kotlinx.android.synthetic.main.list_item.*
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity(), MainView {

  private lateinit var adapter: BoxAdapter<Article>

  private lateinit var presenter: MainPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.main_activity)
    initList()

    presenter = MainPresenter(this)
    presenter.onViewCreated()
  }

  override fun showArticles(articles: List<Article>) {
    // TODO-DMITRY should items be contained inside?
    adapter.items = articles
    adapter.notifyDataSetChanged()
  }

  private fun initList() {
    adapter = createAdapter()

    recycler_view.apply {
      layoutManager = LinearLayoutManager(this@MainActivity)
      addItemDecoration(DividerItemDecoration(resources))
      adapter = this@MainActivity.adapter
    }
  }

  private fun createAdapter() = Box.of<Article>()
    .view(R.layout.list_item)
    .bind { article: Article ->
      article_title.text = article.title
      itemView.onClick {
        // TODO-DMITRY find out how to handle
        "Click: ${article.title}".toast(this@MainActivity)
      }
    }
    .layoutInflater(layoutInflater)
    .build()

}
